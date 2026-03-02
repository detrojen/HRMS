package com.hrms.backend.dtos.requestDto.job;

import com.hrms.backend.dtos.markers.OnUpdate;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReferJobRequestDto {
    @NotBlank(message = "applicants Name required")
    private String applicantsName;
    @NotBlank(message = "applicants email required")
    @Email
    private String applicantsEmail;
    @NotBlank(message = "applicants phone number required")
    @Size(max = 10,min = 10, message = "phone number must be containing 10 digits")
    private String applicantsPhone;
    @NotBlank(message = "applicants details required")
    private String details;
    @NotBlank(message = "cv path required on update",groups = {OnUpdate.class})
    private String cvPath;
}
