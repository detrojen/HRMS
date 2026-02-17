package com.hrms.backend.dtos.requestDto;

import lombok.Data;

@Data
public class ReviewTravelExpenseRequestDto {
    private Long id;
    public String status;
    private int aprrovedAmount;
    private String remark;
}
