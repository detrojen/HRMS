package com.hrms.backend.dtos.responseDtos;

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
    private CvReviewerWithNameOnlyDto[] reviewers;
    private EmployeeWithNameOnlyDto hrOwner;
}
