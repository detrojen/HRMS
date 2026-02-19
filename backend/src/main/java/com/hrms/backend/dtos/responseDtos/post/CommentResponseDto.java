package com.hrms.backend.dtos.responseDtos.post;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String comment;
    private EmployeeMinDetailsDto commentedBy;
}
