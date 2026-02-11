package com.hrms.backend.controllers;

import com.hrms.backend.dtos.requestDto.BookSlotRequestDto;
import com.hrms.backend.dtos.requestDto.CreateUpdateGameTypeRequestDto;
import com.hrms.backend.dtos.requestDto.UpdateGameInterestRequestDto;
import com.hrms.backend.dtos.responseDtos.*;
import com.hrms.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class GameSchedulingController {

    private final GameSlotService gameSlotService;
    private final SlotRequestService slotRequestService;
    private final EmployeeWiseGameInterestService employeeWiseGameInterestService;
    private final GameTypeService gameTypeService;
    @Autowired
    public  GameSchedulingController(GameSlotService gameSlotService,
                                     SlotRequestService slotRequestService,
                                     EmployeeWiseGameInterestService employeeWiseGameInterestService,
                                     GameTypeService gameTypeService
    ){
        this.gameSlotService = gameSlotService;
        this.slotRequestService = slotRequestService;
        this.employeeWiseGameInterestService = employeeWiseGameInterestService;
        this.gameTypeService = gameTypeService;
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

    @GetMapping("/employee-wise-game-interests")
    public ResponseEntity<GlobalResponseDto<List<EmployeeWiseGameInterestResponseDto>>> getInterestedGames(){
        List<EmployeeWiseGameInterestResponseDto> interestedGames = employeeWiseGameInterestService.getEmployeeWiseGameInterests();
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(interestedGames)
        );
    }

    @GetMapping("/game-slots/requested/{requestId}")
    public ResponseEntity<GlobalResponseDto<SlotRequsetResponseDto>> getSlotRequestDetail(@PathVariable Long requestId){
        SlotRequsetResponseDto slotRequsetResponseDto = slotRequestService.getSlotRequestDetail(requestId);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(slotRequsetResponseDto)
        );
    }

    @PutMapping("/game-type/update-interest")
    public ResponseEntity<GlobalResponseDto<EmployeeWiseGameInterestResponseDto>> updateInterest(@RequestBody UpdateGameInterestRequestDto requestDto){
        EmployeeWiseGameInterestResponseDto updated = employeeWiseGameInterestService.updateInterest(requestDto.getGameTypeId(),requestDto.isInterested());
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(updated)
        );
    }


    @GetMapping("/game-types")
    public ResponseEntity<GlobalResponseDto<List<UpdateGameTypeResponseDto>>> getGameTypes(){
        List<UpdateGameTypeResponseDto> gameTypeResponseDtos = gameTypeService.getAllGameTypes();
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(gameTypeResponseDtos)
        );
    }
    @GetMapping("/game-types/{gameTypeId}")
    public ResponseEntity<GlobalResponseDto<UpdateGameTypeResponseDto>> getGameTypeById(@PathVariable Long gameTypeId){
        UpdateGameTypeResponseDto gameTypeResponseDto = gameTypeService.getGameTypeById(gameTypeId);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(gameTypeResponseDto)
        );
    }

    @PostMapping("/game-types")
    public ResponseEntity<GlobalResponseDto<UpdateGameTypeResponseDto>> createGameType(@RequestBody CreateUpdateGameTypeRequestDto requestDto){
        UpdateGameTypeResponseDto gameTypeResponseDto = gameTypeService.createGameType(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(gameTypeResponseDto)
        );
    }
    @PutMapping("/game-types")
    public ResponseEntity<GlobalResponseDto<UpdateGameTypeResponseDto>> updateGameType(@RequestBody CreateUpdateGameTypeRequestDto requestDto){
        UpdateGameTypeResponseDto gameTypeResponseDto = gameTypeService.createGameType(requestDto);
        return ResponseEntity.ok().body(
                new GlobalResponseDto<>(gameTypeResponseDto)
        );
    }
}
