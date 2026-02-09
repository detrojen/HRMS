package com.hrms.backend.services;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.GameSlotResponseDto;
import com.hrms.backend.entities.*;
import com.hrms.backend.repositories.SlotRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SlotRequestService {
    private final SlotRequestRepository slotRequestRepository;
    private final GameSlotService gameSlotService;
    private final EmployeeService employeeService;
    private final SlotRequestWiseemployeeService slotRequestWiseemployeeService;
    private final EmployeeWiseGameInterestService employeeWiseGameInterestService;
    private final GameTypeService gameTypeService;
    @Autowired
    public SlotRequestService(SlotRequestRepository slotRequestRepository, GameSlotService gameSlotService, EmployeeService employeeService, SlotRequestWiseemployeeService slotRequestWiseemployeeService, EmployeeWiseGameInterestService employeeWiseGameInterestService, GameTypeService gameTypeService ){
        this.slotRequestRepository = slotRequestRepository;
        this.gameSlotService = gameSlotService;
        this.employeeService = employeeService;
        this.slotRequestWiseemployeeService = slotRequestWiseemployeeService;
        this.employeeWiseGameInterestService = employeeWiseGameInterestService;
        this.gameTypeService = gameTypeService;
    }
    public int getActiveRequestCount(Long employeeId){
        return slotRequestRepository.getActiveRequestCount(employeeId);
    }

    public SlotRequest getConfirmRequest(Long slotId){
        return slotRequestRepository.findByGameSlot_IdAndStatus(slotId, "Confirm");
    }
    @Transactional
    public String bookSlot(Long slotId, Long[] otherPlayerIds){
        GameSlotResponseDto slotDetails = this.gameSlotService.getSlotById(slotId);

        JwtInfoDto jwtInfo = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSlot slot = this.gameSlotService.getReference(slotId);

        Employee requestedBy = this.employeeService.getReference(jwtInfo.getUserId());

        GameType gameTypeDetails = gameTypeService.getById(slot.getGameType().getId());

        SlotRequest slotRequest = new SlotRequest();

        int activeSlots = slotRequestRepository.getActiveRequestCount(jwtInfo.getUserId());
        int toDaysSlotsCount = slotRequestRepository.getTodaysConsumedSlotCount(jwtInfo.getUserId(), slot.getGameType().getId());
        if(activeSlots != 0){
            throw new RuntimeException("used active request limit");
        }else if(toDaysSlotsCount >= gameTypeDetails.getMaxSlotPerDay()){
            throw new RuntimeException("You reached todays limit");
        }
        slotRequest.setGameSlot(slot);
        slotRequest.setRequestedBy(requestedBy);
        if(slotDetails.isAvailable()){
            gameSlotService.makeSlotUnavailable(slot);
            slotRequest.setStatus("Confirm");
        }else{
            SlotRequest confirmedSlotRequest = getConfirmRequest(slotId);
            if(hasHighPriorityThen(jwtInfo.getUserId(),confirmedSlotRequest.getRequestedBy().getId(), gameTypeDetails.getId())){
                confirmToCancel(confirmedSlotRequest);
                slotRequest.setStatus("Confirm");
            }else {
                slotRequest.setStatus("On Hold");
            }
        }
        gameSlotService.makeSlotUnavailable(slot);
        slotRequest = this.slotRequestRepository.save(slotRequest);
        SlotRequest finalSlotRequest = slotRequest;
        slotRequestWiseemployeeService.mapSlotsToEmployee(jwtInfo.getUserId(), finalSlotRequest);
        employeeWiseGameInterestService.addConsumedSlotCount(jwtInfo.getUserId(),slotDetails.getGameTypeId());

        Arrays.stream(otherPlayerIds).iterator().forEachRemaining(id->{
            slotRequestWiseemployeeService.mapSlotsToEmployee(id,finalSlotRequest);
            employeeWiseGameInterestService.addConsumedSlotCount(id,slotDetails.getGameTypeId());
        });
        return "done";
    }

    public SlotRequest cancelConfirmRequest(Long requestId){
        SlotRequest slotRequest = slotRequestRepository.findById(requestId).orElseThrow(()->new RuntimeException("slot request with this id does not exist"));
        SlotRequest topCandidate = slotRequestRepository.getTopCandidate(slotRequest.getGameSlot().getId());
        confirmToCancel(slotRequest);
        if(topCandidate == null){
            gameSlotService.makeSlotAvailable(slotRequest.getGameSlot());
        }else{
            onHoldToConfirm(topCandidate);
        }
        return slotRequest;
    }

    private boolean hasHighPriorityThen(Long employeeId1, Long employeeId2, Long gameTypeId){
        var emp1GameUsage = this.employeeWiseGameInterestService.getEmployyeGameUsage(employeeId1,gameTypeId);
        var emp2GameUsage = this.employeeWiseGameInterestService.getEmployyeGameUsage(employeeId2,gameTypeId);
        return emp1GameUsage.getCurrentCyclesSlotConsumed() < emp2GameUsage.getCurrentCyclesSlotConsumed();
    }

    private void onHoldToConfirm(SlotRequest slotRequest){
        slotRequest.setStatus("Confirm");
        var emps = slotRequest.getSlotRequestWiseEmployee();
        emps.stream().iterator().forEachRemaining(e-> employeeWiseGameInterestService.addConsumedSlotCount(e.getEmployee().getId(),slotRequest.getGameSlot().getGameType().getId()));
        slotRequestRepository.save(slotRequest);
    }

    private void confirmToCancel(SlotRequest slotRequest){
        slotRequest.setStatus("Canceled");
        var emps = slotRequest.getSlotRequestWiseEmployee();
        emps.stream().iterator().forEachRemaining(e-> employeeWiseGameInterestService.deductConsumedSlotCount(e.getEmployee().getId(), slotRequest.getGameSlot().getGameType().getId()));
        slotRequestRepository.save(slotRequest);
    }

}
