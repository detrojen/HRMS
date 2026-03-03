package com.hrms.backend.dtos.responseDtos.employee;

import lombok.Data;

import java.util.List;

@Data
public class SelfDetailResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private List<RoleResponseDto> roles;
}
