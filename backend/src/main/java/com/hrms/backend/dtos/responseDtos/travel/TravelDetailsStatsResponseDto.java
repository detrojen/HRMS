package com.hrms.backend.dtos.responseDtos.travel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TravelDetailsStatsResponseDto {
    private int totalEmployees;
    private int totalDocuments;
    private int totalEmployeeDocuments;
    private int totalExpenses;
    private int totalAskedExpenseAmount;
    private int totalApprovedExpenseAmount;
    private int totalPendingExpensesToReview;
    private int totalReviewedExpense;
}
