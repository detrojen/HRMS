package com.hrms.backend.dtos.responseDtos.job;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.JobListingEntities.Job;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class JobApplicationResponseDto {
    private Long id;
    private String applicantsName;
    private String applicantsEmail;
    private String applicantsPhone;
    private String details;
    private String cvPath;
    private EmployeeMinDetailsDto referedBy;
    private CreateJobResponseDto job;
}
