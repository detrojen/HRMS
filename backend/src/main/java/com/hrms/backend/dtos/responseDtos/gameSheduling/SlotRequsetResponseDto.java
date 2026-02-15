package com.hrms.backend.dtos.responseDtos.gameSheduling;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import lombok.Data;

import java.util.List;

@Data
public class SlotRequsetResponseDto {
    private  Long id;
    private  String status;
    private GameSlotResponseDto gameSlot;
    private EmployeeWithNameOnlyDto requestedBy;
    private List<SlotRequestWiseEmployeeDto> slotRequestWiseEmployee;
//    private List<EmployeeResponseDto> otherPlayers;
}
