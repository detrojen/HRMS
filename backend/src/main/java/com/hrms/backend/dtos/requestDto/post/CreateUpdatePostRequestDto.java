package com.hrms.backend.dtos.requestDto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUpdatePostRequestDto {
    private Long id;
    @NotBlank(message = "post title is mandatory")
    private String title;
    @NotBlank(message = "post body is mandatory")
    private String body;
    private String[] tags;
}