package com.hrms.backend.dtos.requestDto.job;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CvReviewRequestDto {
    @NotBlank(message = "review field must be required")
    private String review;
}
