package com.hrms.backend.dtos.responseDtos;

import com.hrms.backend.entities.SlotRequestWiseEmployee;
import lombok.Data;

import java.util.List;

@Data
public class SlotRequsetResponseDto {
    private  Long id;
    private  String status;
    private GameSlotResponseDto gameSlot;
    private EmployeeResponseDto requestedBy;
    private List<SlotRequestWiseEmployeeDto> slotRequestWiseEmployee;
//    private List<EmployeeResponseDto> otherPlayers;
}
