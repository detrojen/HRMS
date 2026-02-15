package com.hrms.backend.dtos.requestDto.job;

import lombok.Data;

@Data
public class CreateJobRequestDto {
    private Long id;
    private String title;
    private String description;
    private String workMode;
    private String[] skills;
    private int vacancy;
    private Long[] reviewerIds;
    private Long hrOwnerId;
//    private MultipartFile jdDocument;
}
