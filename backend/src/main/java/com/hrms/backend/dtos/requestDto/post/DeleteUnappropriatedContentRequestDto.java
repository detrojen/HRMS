package com.hrms.backend.dtos.requestDto.post;

import lombok.Data;

@Data
public class DeleteUnappropriatedContentRequestDto {
    private Long id;
    private String remark;
}
