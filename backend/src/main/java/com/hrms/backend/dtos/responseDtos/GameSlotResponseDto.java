package com.hrms.backend.dtos.responseDtos;

import com.hrms.backend.entities.GameType;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class GameSlotResponseDto {
    private Long id;
    private Date slotDate;
    private LocalTime startsFrom;
    private LocalTime endsAt;
    private Long gameTypeId;
    private String gameType;
    private boolean isAvailable;
}
