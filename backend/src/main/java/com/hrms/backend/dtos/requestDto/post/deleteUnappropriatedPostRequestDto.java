package com.hrms.backend.dtos.requestDto.post;

import lombok.Data;

@Data
public class deleteUnappropriatedPostRequestDto {
    private Long id;
    private String remark;
}
