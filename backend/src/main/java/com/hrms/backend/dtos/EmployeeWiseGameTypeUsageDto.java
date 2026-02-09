package com.hrms.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class EmployeeWiseGameTypeUsageDto {
    private Long employeeId;
    private Long gameTypeId;

    private boolean isInterested;

    private int noOfSlotConsumed;

    private int currentCyclesSlotConsumed;

    private boolean isBlocked;
    public EmployeeWiseGameTypeUsageDto(Long gameTypeId , Long employeeId, int noOfSlotConsumed ,  int currentCyclesSlotConsumed, boolean isInterested,boolean isBlocked){
        this.employeeId = employeeId;
        this.gameTypeId = gameTypeId;
        this.noOfSlotConsumed = noOfSlotConsumed;
        this.isBlocked = isBlocked;
        this.isInterested = isInterested;
        this.currentCyclesSlotConsumed = currentCyclesSlotConsumed;
    }


}
