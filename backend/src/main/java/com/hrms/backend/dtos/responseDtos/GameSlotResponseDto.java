package com.hrms.backend.dtos.responseDtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class GameSlotResponseDto {
    private Long id;
    private LocalDate slotDate;
    private LocalTime startsFrom;
    private LocalTime endsAt;
    private Long gameTypeId;
    private String gameType;
    private boolean isAvailable;
}
