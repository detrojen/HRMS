package com.hrms.backend.dtos.requestDto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
