package com.hrms.backend.dtos.requestDto;

import lombok.Data;

@Data
public class deleteUnappropriatedPostRequestDto {
    private Long id;
    private String remark;
}
