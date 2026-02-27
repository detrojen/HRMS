package com.hrms.backend.dtos.responseDtos.job;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobWiseCvReviewerDto {
    private Long id;
    private EmployeeMinDetailsDto reviewer;
}
