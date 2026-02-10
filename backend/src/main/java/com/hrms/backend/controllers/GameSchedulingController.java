package com.hrms.backend.controllers;

import com.hrms.backend.dtos.EmployeeWiseGameTypeUsageDto;
import com.hrms.backend.dtos.requestDto.BookSlotRequestDto;
import com.hrms.backend.dtos.responseDtos.EmployeeResponseDto;
import com.hrms.backend.dtos.responseDtos.GameSlotResponseDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.SlotRequsetResponseDto;
import com.hrms.backend.entities.Employee;
import com.hrms.backend.entities.EmployeeWiseGameInterest;
import com.hrms.backend.entities.SlotRequest;
import com.hrms.backend.repositories.EmployeeWiseGameInterestRepository;
import com.hrms.backend.services.EmployeeService;
import com.hrms.backend.services.EmployeeWiseGameInterestService;
import com.hrms.backend.services.GameSlotService;
import com.hrms.backend.services.SlotRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
public class GameSchedulingController {

    private final GameSlotService gameSlotService;
    private final SlotRequestService slotRequestService;
    private final EmployeeWiseGameInterestService employeeWiseGameInterestService;

    @Autowired
    public  GameSchedulingController(GameSlotService gameSlotService, SlotRequestService slotRequestService,EmployeeWiseGameInterestService employeeWiseGameInterestService){
        this.gameSlotService = gameSlotService;
        this.slotRequestService = slotRequestService;
        this.employeeWiseGameInterestService = employeeWiseGameInterestService;
    }

    @GetMapping("/game-slots/{gameTypeId}")
    public ResponseEntity<GlobalResponseDto<List<GameSlotResponseDto>>> getGameSlotsByDate(@PathVariable Long gameTypeId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date slotDate){
        List<GameSlotResponseDto> slots = this.gameSlotService.getGameSlotsByDate(gameTypeId,slotDate);
        return ResponseEntity.ok().body(new GlobalResponseDto<List<GameSlotResponseDto>>(slots));
    }

    @PostMapping("/game-slot/book")
    public ResponseEntity<GlobalResponseDto<SlotRequsetResponseDto>> bookSlot(@RequestBody BookSlotRequestDto requestDto){
        SlotRequsetResponseDto bookedSlot = this.slotRequestService.bookSlot(requestDto.getSlotId(), requestDto.getOtherPlayersId());
        return ResponseEntity.ok().body(new GlobalResponseDto<>(bookedSlot,"Your slot is requested",null));
    }

    @DeleteMapping("/game-slots/cancel/{slotRequestId}")
    public SlotRequsetResponseDto cancel(@PathVariable Long slotRequestId){
        return slotRequestService.cancelRequest(slotRequestId);
    }

    @GetMapping("/employee/interested-game/{gameTypeId}")
    public ResponseEntity<GlobalResponseDto<List<EmployeeResponseDto>>> getInterestedEmployees(@PathVariable Long gameTypeId, @RequestParam String nameLike){
        var data = employeeWiseGameInterestService.getEmployeeOfGameIntersetOf(gameTypeId,nameLike);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(data));
    }

    @GetMapping("/game-slots/active")
    public ResponseEntity<GlobalResponseDto<List<SlotRequsetResponseDto>>> getActiveSlots(){
        List<SlotRequsetResponseDto> slotRequsetResponseDtos = slotRequestService.getActiveSlotRequests();
        return ResponseEntity.ok(new GlobalResponseDto<>(slotRequsetResponseDtos));
    }


}
