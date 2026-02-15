package com.hrms.backend.dtos.responseDtos.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvReviewerWithNameOnlyDto {
    private Long id;
    private String firstName;
    private String lastName;
}
