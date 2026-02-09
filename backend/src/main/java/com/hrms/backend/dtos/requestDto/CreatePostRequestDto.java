package com.hrms.backend.dtos.requestDto;

import lombok.Data;

@Data
public class CreatePostRequestDto {
    private String title;
    private String body;
    private String tags;
}