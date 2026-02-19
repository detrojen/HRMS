package com.hrms.backend.dtos.responseDtos.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithManagerIdDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String designation;
    private Long managerId;

}
