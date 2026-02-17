package com.hrms.backend.dtos.requestDto.travel;

import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import lombok.Data;

import java.time.LocalDate;
@Data
public class AddUpdateTravelExpenseRequestDto {
    private  Long id;
    private String description;
    private Long categoryId;
    private int askedAmount;
    private String reciept;
    private LocalDate dateOfExpense;
}
