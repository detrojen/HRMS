package com.hrms.backend.dtos.requestDto.post;

import lombok.Data;

@Data
public class CreatePostRequestDto {
    private String title;
    private String body;
    private String[] tags;
}