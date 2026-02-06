package com.hrms.backend.dtos.requestDtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePostRequestDto {
    private String title;
    private String body;
    private String tags;
}