package com.hrms.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithManagerIdDto {
    private Long id;
    private String firstName;
    private Long managerId;


}
