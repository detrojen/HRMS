package com.hrms.backend.dtos.requestDto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUpdateCommentRequestDto {
    private Long id;
    @NotBlank(message = "comment not be blank")
    private String comment;
}
