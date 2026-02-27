package com.hrms.backend.dtos.responseDtos.job;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import lombok.Data;

@Data
public class CvReviewResponseDto {
    private Long id;
    private String review;
    private EmployeeMinDetailsDto reviewedBy;
}
