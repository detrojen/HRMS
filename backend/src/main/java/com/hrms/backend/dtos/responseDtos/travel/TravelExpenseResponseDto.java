package com.hrms.backend.dtos.responseDtos.travel;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelExpenseResponseDto {
    private  Long id;
    private String description;
    private ExpenseCategoryDto category;
    private int askedAmount;
    private String reciept;
    private LocalDate dateOfExpense;
    private EmployeeWithNameOnlyDto employee;
    private  EmployeeWithNameOnlyDto reviewedBy;
    private int aprrovedAmount;
    private String remark;
    private String status;
}
