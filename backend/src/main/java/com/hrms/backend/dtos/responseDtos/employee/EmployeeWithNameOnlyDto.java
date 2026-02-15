package com.hrms.backend.dtos.responseDtos.employee;

import lombok.Data;

@Data
public class EmployeeWithNameOnlyDto {
    private  Long id;
    private  String firstName;
    private  String lastName;
}
