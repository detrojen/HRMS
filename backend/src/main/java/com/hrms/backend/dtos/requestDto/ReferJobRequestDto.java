package com.hrms.backend.dtos.requestDto;

import lombok.Data;

@Data
public class ReferJobRequestDto {
    private String applicantsName;
    private String applicantsEmail;
    private String applicantsPhone;
    private String details;
    private String cvPath;
}
