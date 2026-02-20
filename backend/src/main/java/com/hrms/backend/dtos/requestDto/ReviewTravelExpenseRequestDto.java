package com.hrms.backend.dtos.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ReviewTravelExpenseRequestDto {
    @NotNull
    private Long id;
    @PositiveOrZero(message = "aprrovedAmount should be postive or zero")
    private int aprrovedAmount;
    @NotBlank(message = "remark is required to review expense")
    private String remark;
}
