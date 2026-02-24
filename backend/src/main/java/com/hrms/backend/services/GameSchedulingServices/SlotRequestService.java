package com.hrms.backend.services.GameSchedulingServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.dtos.responseDtos.gameSheduling.CurrentGameStatusResponse;
import com.hrms.backend.dtos.responseDtos.gameSheduling.GameSlotResponseDto;
import com.hrms.backend.dtos.responseDtos.gameSheduling.SlotRequsetResponseDto;
import com.hrms.backend.emailTemplates.GameSchedulingEmailTemplate;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.GameSchedulingEntities.GameSlot;
import com.hrms.backend.entities.GameSchedulingEntities.GameType;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequestWiseEmployee;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.exceptions.SlotCanNotBeBookedException;
import com.hrms.backend.repositories.GameSchedulingRepositories.SlotRequestRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.services.NotificationServices.NotificationService;
import com.hrms.backend.specs.SlotRequestSpecs;
import com.hrms.backend.tasks.SlotRequestEvaluteTask;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SlotRequestService {
    private final SlotRequestRepository slotRequestRepository;
    private final GameSlotService gameSlotService;
    private final EmployeeService employeeService;
    private final SlotRequestWiseemployeeService slotRequestWiseemployeeService;
    private final EmployeeWiseGameInterestService employeeWiseGameInterestService;
    private final GameTypeService gameTypeService;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final NotificationService notificationService;
    @Autowired
    public SlotRequestService(
            SlotRequestRepository slotRequestRepository
            , GameSlotService gameSlotService
            , EmployeeService employeeService
            , SlotRequestWiseemployeeService slotRequestWiseemployeeService
            , EmployeeWiseGameInterestService employeeWiseGameInterestService
            , GameTypeService gameTypeService
            , ModelMapper modelMapper
            , EmailService emailService
            , NotificationService notificationService
    ){
        this.slotRequestRepository = slotRequestRepository;
        this.gameSlotService = gameSlotService;
        this.employeeService = employeeService;
        this.slotRequestWiseemployeeService = slotRequestWiseemployeeService;
        this.employeeWiseGameInterestService = employeeWiseGameInterestService;
        this.gameTypeService = gameTypeService;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    public int getActiveRequestCount(Long employeeId, Long gameTypeId, LocalDate reqDate){
        return slotRequestRepository.getActiveRequestCount(employeeId,gameTypeId, reqDate);
    }

    public List<SlotRequsetResponseDto> getActiveSlotRequests(){
        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Specification<SlotRequest> specs =  SlotRequestSpecs.getActiveSlotsSpecs(jwtInfo.getUserId());
        List<SlotRequest> requests = slotRequestRepository.findAll(specs);
        return requests.stream().map(request->modelMapper.map(request,SlotRequsetResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public SlotRequsetResponseDto getSlotRequestDetail(Long id){
        SlotRequest slotRequest = slotRequestRepository.findById(id).orElseThrow(()->new ItemNotFoundExpection("slot request not found"));
        return modelMapper.map(slotRequest,SlotRequsetResponseDto.class);
    }

    public SlotRequest getConfirmRequest(Long slotId){
        return slotRequestRepository.findByGameSlot_IdAndStatus(slotId, "Confirm");
    }

    public void validateSlotRequest( GameType gameTypeDetails,GameSlot slot,List<Long> otherPlayerIds){
        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int activeSlots = slotRequestRepository.getActiveRequestCount(jwtInfo.getUserId(), slot.getGameType().getId(),slot.getSlotDate());
        int toDaysSlotsCount = slotRequestRepository.getTodaysConsumedSlotCount(jwtInfo.getUserId(), slot.getGameType().getId(),slot.getSlotDate());
        if(activeSlots >= gameTypeDetails.getMaxActiveSlotPerDay()){
            throw new SlotCanNotBeBookedException("used active request limit");
        }
        if(toDaysSlotsCount >= gameTypeDetails.getMaxSlotPerDay()) {
            throw new SlotCanNotBeBookedException("You reached todays limit");
        }
        if(otherPlayerIds.size()>gameTypeDetails.getMaxNoOfPlayers()){
            throw new SlotCanNotBeBookedException("No players are greater then allowed.Only "+ gameTypeDetails.getMaxNoOfPlayers() + " are allowed.");
        }
        if( LocalDate.now().isAfter(slot.getSlotDate()) || LocalDate.now().isEqual(slot.getSlotDate()) && (LocalTime.now().isAfter(slot.getStartsFrom()))){
            throw new SlotCanNotBeBookedException("booking slot in past not allowed");
        }
        if(otherPlayerIds.stream().map(id->employeeWiseGameInterestService.getEmployyeGameUsage(id,gameTypeDetails.getId()).isInterested()).toList().stream().anyMatch(flag->flag==false)){
            throw new SlotCanNotBeBookedException("One of the player is not interested in this game.");
        }
    }

    @Transactional
    public SlotRequsetResponseDto bookSlot(Long slotId, List<Long> otherPlayerIds){
        GameSlotResponseDto slotDetails = this.gameSlotService.getSlotById(slotId);

        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        otherPlayerIds.add(jwtInfo.getUserId());
        GameSlot slot = this.gameSlotService.getReference(slotId);



        GameType gameTypeDetails = gameTypeService.getById(slot.getGameType().getId());

        validateSlotRequest(gameTypeDetails,slot,otherPlayerIds);
        Employee requestedBy = this.employeeService.getReference(jwtInfo.getUserId());
        SlotRequest slotRequest = new SlotRequest();

        slotRequest.setGameSlot(slot);
        slotRequest.setRequestedBy(requestedBy);

        if(slotDetails.isAvailable()){
            gameSlotService.makeSlotUnavailable(slot);
            slotRequest.setStatus("Confirm");
            otherPlayerIds.iterator().forEachRemaining(id->{
                employeeWiseGameInterestService.addConsumedSlotCount(id,slotDetails.getGameTypeId());
            });
        }else{
            SlotRequest confirmedSlotRequest = getConfirmRequest(slotId);
            if(hasHighPriorityThen(jwtInfo.getUserId(),confirmedSlotRequest.getRequestedBy().getId(), gameTypeDetails.getId())){
                confirmToCancelOrOnHold(confirmedSlotRequest,"On hold");
                slotRequest.setStatus("Confirm");
                otherPlayerIds.iterator().forEachRemaining(id->{
                    employeeWiseGameInterestService.addConsumedSlotCount(id,slotDetails.getGameTypeId());
                });
            }else {
                slotRequest.setStatus("On Hold");
            }
        }

        slotRequest = this.slotRequestRepository.save(slotRequest);
        SlotRequest finalSlotRequest = slotRequest;


        List<SlotRequestWiseEmployee> mappedEmployees = otherPlayerIds.stream().map(id->slotRequestWiseemployeeService.mapSlotsToEmployee(id,finalSlotRequest)).collect(Collectors.toUnmodifiableList());

        slotRequest.setSlotRequestWiseEmployee(mappedEmployees);
        notifyAndShareEmail(slotRequest);
        return modelMapper.map(slotRequest, SlotRequsetResponseDto.class);

    }

    public SlotRequsetResponseDto cancelConfirmRequest(SlotRequest slotRequest){
//        SlotRequest slotRequest = slotRequestRepository.findById(requestId).orElseThrow(()->new ItemNotFoundExpection("slot request with this id does not exist"));
        SlotRequest topCandidate = slotRequestRepository.getTopCandidate(slotRequest.getGameSlot().getId(),slotRequest.getGameSlot().getGameType().getId());
        confirmToCancelOrOnHold(slotRequest,"Cancel");
        if(topCandidate == null){
            gameSlotService.makeSlotAvailable(slotRequest.getGameSlot());
        }else{
            onHoldToConfirm(topCandidate);
        }
        return modelMapper.map(slotRequest,SlotRequsetResponseDto.class);

    }

    public SlotRequsetResponseDto cancelRequest(Long requestId){
        SlotRequest slotRequest = slotRequestRepository.findById(requestId).orElseThrow(()->new ItemNotFoundExpection("slot request with this id does not exist"));
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(jwtInfoDto.getUserId() != slotRequest.getRequestedBy().getId()){
            throw new InvalidActionException("slot only can be cancelled by who has reqyested");
        }
        if(slotRequest.getStatus().equals("Confirm")){
            return cancelConfirmRequest(slotRequest);
        }
        SlotRequest sr = onHoldToCancel(slotRequest);

        return modelMapper.map(sr,SlotRequsetResponseDto.class);
    }

    private boolean hasHighPriorityThen(Long employeeId1, Long employeeId2, Long gameTypeId){
        var emp1GameUsage = this.employeeWiseGameInterestService.getEmployyeGameUsage(employeeId1,gameTypeId);
        var emp2GameUsage = this.employeeWiseGameInterestService.getEmployyeGameUsage(employeeId2,gameTypeId);
        return (emp2GameUsage.getCurrentCyclesSlotConsumed() - emp1GameUsage.getCurrentCyclesSlotConsumed()) > 1 ;
    }

    private SlotRequest onHoldToConfirm(SlotRequest slotRequest){
        slotRequest.setStatus("Confirm");
        var employees = slotRequest.getSlotRequestWiseEmployee();
        employees.stream().iterator().forEachRemaining(e-> employeeWiseGameInterestService.addConsumedSlotCount(e.getEmployee().getId(),slotRequest.getGameSlot().getGameType().getId()));
        notifyAndShareEmail(slotRequest);
        return slotRequestRepository.save(slotRequest);
    }

    private SlotRequest confirmToCancelOrOnHold(SlotRequest slotRequest, String status){
        slotRequest.setStatus(status);
        var employees = slotRequest.getSlotRequestWiseEmployee();
        employees.stream().iterator().forEachRemaining(e-> employeeWiseGameInterestService.deductConsumedSlotCount(e.getEmployee().getId(), slotRequest.getGameSlot().getGameType().getId()));
        notifyAndShareEmail(slotRequest);
        return slotRequestRepository.save(slotRequest);
    }

    private SlotRequest onHoldToCancel(SlotRequest slotRequest){
        slotRequest.setStatus("Cancel");
        notifyAndShareEmail(slotRequest);
        return slotRequestRepository.save(slotRequest);
    }

    private void notifyAndShareEmail(SlotRequest slotRequest){
        emailService.sendMail(
                "Slot request ackwolwdgement"
                , GameSchedulingEmailTemplate.forSlotRequest(slotRequest)
                ,slotRequest.getSlotRequestWiseEmployee().stream().map(e->e.getEmployee().getEmail()).toList().toArray(new String[]{})
                ,new String[]{}
        );
        notificationService.notify(
                "Slot reuqested for " + slotRequest.getGameSlot().getGameType().getGame() + " on " + slotRequest.getGameSlot().getSlotDate() + " from "  + slotRequest.getGameSlot().getStartsFrom() + " to "  +slotRequest.getGameSlot().getEndsAt()
            ,"Game"
                ,slotRequest.getSlotRequestWiseEmployee().stream().map(e->e.getEmployee().getId()).toList().toArray(new Long[]{})
        );
    }

    private List<EmployeeMinDetailsDto> getPlayers(Long slotId){
        SlotRequest slotRequest = slotRequestRepository.findByGameSlot_IdAndStatus(slotId,"Confirm");
        return slotRequest.getSlotRequestWiseEmployee().stream().map(slotWiseEmp->modelMapper.map(slotWiseEmp.getEmployee(),EmployeeMinDetailsDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public List<CurrentGameStatusResponse> getCurrentGameStatus(){
        List<GameSlotResponseDto> slots = gameSlotService.getCurrentGameSlots();
        List<CurrentGameStatusResponse> games = new ArrayList<>();
        slots.forEach(slot->{
            CurrentGameStatusResponse game = new CurrentGameStatusResponse();
            game.setGameSlot(slot);
            if(!slot.isAvailable()){
                game.setPlayers(getPlayers(slot.getId()));
            }else{
                game.setPlayers(new ArrayList<>());
            }
            games.add(game);
        });
        return games;
    }

}
