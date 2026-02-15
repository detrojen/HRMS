package com.hrms.backend.dtos.responseDtos.gameSheduling;

import lombok.Data;

@Data
public class EmployeeWiseGameInterestResponseDto {
    private Long id;
    private boolean isInterested;
    private int noOfSlotConsumed;
    private int currentCyclesSlotConsumed;
    private boolean isBlocked;
    private Long gameTypeId;
    private String gameType;

}
