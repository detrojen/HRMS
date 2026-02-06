package com.hrms.backend.dtos.responseDtos;

import lombok.Data;

@Data
public class DeletePostResponseDto extends PostResponseDto{
    private String remark;
    private String deletedBy;
}
