package com.hrms.backend.dtos.responseDtos.gameSheduling;

import com.hrms.backend.dtos.requestDto.gameScheduling.CreateUpdateGameTypeRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class UpdateGameTypeResponseDto extends CreateUpdateGameTypeRequestDto {
    private  Long id;
    private String game;
    private int slotDuration;
    private LocalTime openingHours;
    private LocalTime closingHours;
    private int maxNoOfPlayers;
    private Long slotCanBeBookedBefore;
    private boolean isInMaintenance;
    private int maxSlotPerDay;
    private int maxActiveSlotPerDay;
    private int noOfInteretedEmployees;
}
