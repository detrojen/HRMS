package com.hrms.backend.dtos.responseDtos.travel;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TravelDetailResponseDto {
    private Long id;
    private String title;
    private String descripton;
    private int maxReimbursementAmountPerDay;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastDateToSubmitExpense;
    private List<EmployeeWithNameOnlyDto> employees;
    private EmployeeWithNameOnlyDto initiatedBy;
    private List<TravelDocumentResponseDto> travelDocuments;
}
