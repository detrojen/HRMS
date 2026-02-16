package com.hrms.backend.dtos.responseDtos.employee;

import lombok.Data;

@Data
public class SelfDetailResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
