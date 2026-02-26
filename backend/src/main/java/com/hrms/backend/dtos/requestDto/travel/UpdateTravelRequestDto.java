package com.hrms.backend.dtos.requestDto.travel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTravelRequestDto {
    private  Long id;
    @NotBlank(message = "desricption is mandatory")
    private String descripton;
    @NotBlank(message = "title is mandatory")
    private String title;
    @NotNull(message = "maximum reimbursement amount is mandatory to provide")
    @PositiveOrZero
    private int maxReimbursementAmountPerDay;
    @NotNull(message = "start date  is mandatory")
    private LocalDate startDate;
    @NotNull(message = "end date  is mandatory")
    private LocalDate endDate;
    @NotNull(message = "last date expense is mandatory")
    private LocalDate lastDateToSubmitExpense;
}
