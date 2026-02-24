package com.hrms.backend.dtos.responseDtos.gameSheduling;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import lombok.Data;

import java.util.List;

@Data
public class CurrentGameStatusResponse {
    private List<EmployeeMinDetailsDto> players;
    private GameSlotResponseDto gameSlot;
}
