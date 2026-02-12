package com.hrms.backend.dtos.requestDto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
