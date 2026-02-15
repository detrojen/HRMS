package com.hrms.backend.dtos.responseDtos.gameSheduling;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import lombok.Data;

@Data
public class SlotRequestWiseEmployeeDto {
    private EmployeeWithNameOnlyDto employee;
}
