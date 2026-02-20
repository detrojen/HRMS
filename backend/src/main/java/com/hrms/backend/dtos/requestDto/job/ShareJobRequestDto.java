package com.hrms.backend.dtos.requestDto.job;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShareJobRequestDto {
    @NotBlank(message = "email required")
    @Email()
    private String email;
}
