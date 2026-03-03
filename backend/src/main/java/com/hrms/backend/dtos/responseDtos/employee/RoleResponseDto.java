package com.hrms.backend.dtos.responseDtos.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleResponseDto {
    private Long id;
    private String roleTitle;
    public RoleResponseDto(Long id, String roleTitle){
        this.id=id;
        this.roleTitle = roleTitle;
    }
}
