package com.hrms.backend.dtos.requestDto.job;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewJobApplicationRequestDto {
    @NotBlank(message = "remark is mandatory")
    private String remark;
    @NotBlank(message = "status is mandatory")
    private String status;
}
