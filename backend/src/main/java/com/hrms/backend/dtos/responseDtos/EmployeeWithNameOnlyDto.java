package com.hrms.backend.dtos.responseDtos;

import lombok.Data;

@Data
public class EmployeeWithNameOnlyDto {
    private  Long id;
    private  String firstName;
    private  String lastName;
}
