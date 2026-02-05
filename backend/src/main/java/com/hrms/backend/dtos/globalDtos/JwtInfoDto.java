package com.hrms.backend.dtos.globalDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtInfoDto {
    private Long userId;
    private String email;
    private String roleTitle;
}
