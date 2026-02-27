package com.hrms.backend.dtos.responseDtos.job;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class JobApplicationResponseDto {
    private Long id;
    private String applicantsName;
    private String applicantsEmail;
    private String applicantsPhone;
    private String details;
    private String cvPath;
    private String status;
    private String remark;
    private EmployeeMinDetailsDto reviewedBy;
    private EmployeeMinDetailsDto referedBy;
    private CreateJobResponseDto job;
    private List<CvReviewResponseDto> cvReviews;
}
