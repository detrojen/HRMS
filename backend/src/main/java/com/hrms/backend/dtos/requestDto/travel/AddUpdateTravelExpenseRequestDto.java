package com.hrms.backend.dtos.requestDto.travel;

import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import lombok.Data;

import java.time.LocalDate;
@Data
public class AddUpdateTravelExpenseRequestDto {
    private  Long id;
    private Long categoryId;
    private int askedAmout;
    private String reciept;
    private LocalDate dateOfExpense;
}
