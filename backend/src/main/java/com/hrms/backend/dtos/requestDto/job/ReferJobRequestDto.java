package com.hrms.backend.dtos.requestDto.job;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReferJobRequestDto {
    @NotBlank(message = "applicants Name required")
    private String applicantsName;
    @NotBlank(message = "applicants email required")
    @Email
    private String applicantsEmail;
    @NotBlank(message = "applicants phone number required")
    @Max(value = 10, message = "phone numbe must be 10 digit long")
    @Min(value = 10, message = "phone numbe must be 10 digit long")
    private String applicantsPhone;
    @NotBlank(message = "applicants details required")
    private String details;
    @NotBlank(message = "cv path required on update")
    private String cvPath;
}
