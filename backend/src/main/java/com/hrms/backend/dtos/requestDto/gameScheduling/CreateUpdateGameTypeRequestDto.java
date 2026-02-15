package com.hrms.backend.dtos.requestDto.gameScheduling;

import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateUpdateGameTypeRequestDto {
    private Long id;
    private String game;
    private int slotDuration;
    private LocalTime openingHours;
    private LocalTime closingHours;
    private int maxNoOfPlayers;
    private Long slotCanBeBookedBefore;
    private boolean isInMaintenance;
    private int maxSlotPerDay;
    private int maxActiveSlotPerDay;
}
