package com.hrms.backend.dtos.responseDtos.travel;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeWithNameOnlyDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;


public class TravelMinDetailResponseDto {
    private Long id;
    private String title;
    private String descripton;
    private int maxReimbursementAmountPerDay;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastDateToSubmitExpense;
    private EmployeeWithNameOnlyDto initiatedBy;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public int getMaxReimbursementAmountPerDay() {
        return maxReimbursementAmountPerDay;
    }

    public void setMaxReimbursementAmountPerDay(int maxReimbursementAmountPerDay) {
        this.maxReimbursementAmountPerDay = maxReimbursementAmountPerDay;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getLastDateToSubmitExpense() {
        return lastDateToSubmitExpense;
    }

    public void setLastDateToSubmitExpense(LocalDate lastDateToSubmitExpense) {
        this.lastDateToSubmitExpense = lastDateToSubmitExpense;
    }

    public EmployeeWithNameOnlyDto getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(EmployeeWithNameOnlyDto initiatedBy) {
        this.initiatedBy = initiatedBy;
    }
}
