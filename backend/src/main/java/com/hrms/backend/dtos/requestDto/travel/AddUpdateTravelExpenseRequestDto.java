package com.hrms.backend.dtos.requestDto.travel;

import com.hrms.backend.dtos.markers.OnUpdate;
import com.hrms.backend.entities.TravelEntities.ExpenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class AddUpdateTravelExpenseRequestDto {
    @NotNull(message = "id should be provided in update request",groups = {OnUpdate.class})
    private  Long id;
    @NotBlank
    private String description;
    @NotNull
    private Long categoryId;
    @NotNull
    private int askedAmount;
    @NotBlank(message = "reciept should be provided in update request",groups = {OnUpdate.class})
    private String reciept;
    @NotNull
    private LocalDate dateOfExpense;
}
