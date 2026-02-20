package com.hrms.backend.dtos.requestDto.travel;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateTravelRequestDto {
    private  Long id;
    @NotBlank
    private String descripton;
    @NotBlank
    private String title;
    @NotBlank
    @PositiveOrZero
    private int maxReimbursementAmountPerDay;
    @NotBlank
    private LocalDate startDate;
    @NotBlank
    private LocalDate endDate;
    @NotBlank
    private LocalDate lastDateToSubmitExpense;
    private List<Long> employeeIds;
}
