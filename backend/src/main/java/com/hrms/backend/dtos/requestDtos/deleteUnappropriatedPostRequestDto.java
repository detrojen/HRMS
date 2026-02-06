package com.hrms.backend.dtos.requestDtos;

import lombok.Data;

@Data
public class deleteUnappropriatedPostRequestDto {
    private Long id;
    private String remark;
}
