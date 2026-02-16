package com.hrms.backend.dtos.responseDtos.travel;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelExpenseResponseDto {
    private  Long id;
    private ExpenseCategoryDto category;
    private int askedAmout;
    private String reciept;
    private LocalDate dateOfExpense;
    private EmployeeWithNameOnlyDto employee;
    private  EmployeeWithNameOnlyDto reviedBy;
    private int aprrovedAmout;
    private String remark;
}
