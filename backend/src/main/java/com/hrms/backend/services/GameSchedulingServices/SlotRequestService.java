package com.hrms.backend.services.GameSchedulingServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.GameSlotResponseDto;
import com.hrms.backend.dtos.responseDtos.SlotRequsetResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.GameSchedulingEntities.GameSlot;
import com.hrms.backend.entities.GameSchedulingEntities.GameType;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;
import com.hrms.backend.entities.GameSchedulingEntities.SlotRequestWiseEmployee;
import com.hrms.backend.exceptions.SlotCanNotBeBookedException;
import com.hrms.backend.repositories.GameSchedulingRepositories.SlotRequestRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.specs.SlotRequestSpecs;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotRequestService {
    private final SlotRequestRepository slotRequestRepository;
    private final GameSlotService gameSlotService;
    private final EmployeeService employeeService;
    private final SlotRequestWiseemployeeService slotRequestWiseemployeeService;
    private final EmployeeWiseGameInterestService employeeWiseGameInterestService;
    private final GameTypeService gameTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public SlotRequestService(SlotRequestRepository slotRequestRepository, GameSlotService gameSlotService, EmployeeService employeeService, SlotRequestWiseemployeeService slotRequestWiseemployeeService, EmployeeWiseGameInterestService employeeWiseGameInterestService, GameTypeService gameTypeService ,ModelMapper modelMapper){
        this.slotRequestRepository = slotRequestRepository;
        this.gameSlotService = gameSlotService;
        this.employeeService = employeeService;
        this.slotRequestWiseemployeeService = slotRequestWiseemployeeService;
        this.employeeWiseGameInterestService = employeeWiseGameInterestService;
        this.gameTypeService = gameTypeService;
        this.modelMapper = modelMapper;
    }

    public int getActiveRequestCount(Long employeeId, Long gameTypeId){
        return slotRequestRepository.getActiveRequestCount(employeeId,gameTypeId);
    }

    public List<SlotRequsetResponseDto> getActiveSlotRequests(){
        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Specification<SlotRequest> specs =  SlotRequestSpecs.getActiveSlotsSpecs(jwtInfo.getUserId());
        List<SlotRequest> requests = slotRequestRepository.findAll(specs);
        return requests.stream().map(request->modelMapper.map(request,SlotRequsetResponseDto.class)).collect(Collectors.toUnmodifiableList());
    }

    public SlotRequsetResponseDto getSlotRequestDetail(Long id){
        SlotRequest slotRequest = slotRequestRepository.findById(id).orElseThrow(()->new RuntimeException("slot request not found"));
        return modelMapper.map(slotRequest,SlotRequsetResponseDto.class);
    }

    public SlotRequest getConfirmRequest(Long slotId){
        return slotRequestRepository.findByGameSlot_IdAndStatus(slotId, "Confirm");
    }

    @Transactional
    public SlotRequsetResponseDto bookSlot(Long slotId, List<Long> otherPlayerIds){
        GameSlotResponseDto slotDetails = this.gameSlotService.getSlotById(slotId);

        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSlot slot = this.gameSlotService.getReference(slotId);

        Employee requestedBy = this.employeeService.getReference(jwtInfo.getUserId());

        GameType gameTypeDetails = gameTypeService.getById(slot.getGameType().getId());

        SlotRequest slotRequest = new SlotRequest();

        int activeSlots = slotRequestRepository.getActiveRequestCount(jwtInfo.getUserId(), slot.getGameType().getId());
        int toDaysSlotsCount = slotRequestRepository.getTodaysConsumedSlotCount(jwtInfo.getUserId(), slot.getGameType().getId());
        if(activeSlots >= gameTypeDetails.getMaxActiveSlotPerDay()){
            throw new SlotCanNotBeBookedException("used active request limit");
        }else if(toDaysSlotsCount >= gameTypeDetails.getMaxSlotPerDay()) {

            throw new SlotCanNotBeBookedException("You reached todays limit");
        }
        slotRequest.setGameSlot(slot);
        slotRequest.setRequestedBy(requestedBy);
        otherPlayerIds.add(jwtInfo.getUserId());
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
        return modelMapper.map(slotRequest, SlotRequsetResponseDto.class);

    }

    public SlotRequsetResponseDto cancelConfirmRequest(SlotRequest slotRequest){
//        SlotRequest slotRequest = slotRequestRepository.findById(requestId).orElseThrow(()->new RuntimeException("slot request with this id does not exist"));
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
        SlotRequest slotRequest = slotRequestRepository.findById(requestId).orElseThrow(()->new RuntimeException("slot request with this id does not exist"));
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
        return slotRequestRepository.save(slotRequest);
    }

    private SlotRequest confirmToCancelOrOnHold(SlotRequest slotRequest, String status){
        slotRequest.setStatus(status);
        var employees = slotRequest.getSlotRequestWiseEmployee();
        employees.stream().iterator().forEachRemaining(e-> employeeWiseGameInterestService.deductConsumedSlotCount(e.getEmployee().getId(), slotRequest.getGameSlot().getGameType().getId()));
        return slotRequestRepository.save(slotRequest);
    }

    private SlotRequest onHoldToCancel(SlotRequest slotRequest){
        slotRequest.setStatus("Canceled");
        return slotRequestRepository.save(slotRequest);
    }

}
