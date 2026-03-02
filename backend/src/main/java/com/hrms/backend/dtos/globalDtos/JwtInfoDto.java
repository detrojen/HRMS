package com.hrms.backend.dtos.globalDtos;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JwtInfoDto {
    private Long userId;
    private String email;
    private String roleTitle;
    private String fullName;
}
