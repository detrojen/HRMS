package com.hrms.backend.dtos.requestDto.travel;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateTravelRequestDto {
    private  Long id;
    private String descripton;
    private String title;
    private int maxReimbursementAmountPerDay;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastDateToSubmitExpense;
    private List<Long> employeeIds;
}
