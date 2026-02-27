package com.hrms.backend.dtos.responseDtos.job;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import lombok.Data;

@Data
public class CreateJobResponseDto {
    private Long id;
    private String title;
    private String description;
    private String workMode;
    private String skills;
    private String status;
    private int vacancy;
    private String jdPath;
    private JobWiseCvReviewerDto[] reviewers;
    private EmployeeWithNameOnlyDto hrOwner;
}
