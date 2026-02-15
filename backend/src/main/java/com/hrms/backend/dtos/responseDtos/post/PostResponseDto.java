package com.hrms.backend.dtos.responseDtos.post;

import lombok.Data;

@Data
public class PostResponseDto {
    private Long id;
    private String title;
    private String body;
    private String attachmentPath;
    private String tags;
    private String createdBy;

}
