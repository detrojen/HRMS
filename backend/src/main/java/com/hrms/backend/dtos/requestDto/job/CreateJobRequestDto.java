package com.hrms.backend.dtos.requestDto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateJobRequestDto {
    private Long id;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "work mode is required")
    private String workMode;
    private String[] skills;
    @Positive
    private int vacancy;
    private Long[] reviewerIds;
    @NotNull(message = "hr owner required")
    private Long hrOwnerId;
//    private MultipartFile jdDocument;
}
