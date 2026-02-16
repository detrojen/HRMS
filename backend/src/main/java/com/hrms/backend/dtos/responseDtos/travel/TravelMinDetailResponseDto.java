package com.hrms.backend.dtos.responseDtos.travel;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelMinDetailResponseDto {
    private Long id;
    private String title;
    private String descripton;
    private int maxReimbursementAmountPerDay;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastDateToSubmitExpense;
    private EmployeeWithNameOnlyDto initiatedBy;
}
