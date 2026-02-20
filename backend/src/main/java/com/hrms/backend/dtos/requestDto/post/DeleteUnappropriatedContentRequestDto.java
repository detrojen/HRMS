package com.hrms.backend.dtos.requestDto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteUnappropriatedContentRequestDto {
    @NotBlank
    private Long id;
    @NotBlank(message = "remark for delete unapropriate content is required")
    private String remark;
}
