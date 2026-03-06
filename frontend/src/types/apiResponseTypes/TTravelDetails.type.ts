import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type";
import type { TTravelDoucmentResponse } from "./TTravelDocumnetResponse.type";
import type { TTravelExpenseResponse } from "./TTravelExpenseResponse.type";

export type TTravelDetails = {
    id: number
    title: string
    descripton: string
    maxReimbursementAmountPerDay: number
    startDate: string
    endDate: string
    lastDateToSubmitExpense: string
    employees: TEmployeeWithNameOnly[];
    initiatedBy: TEmployeeWithNameOnly;
    travelDocuments: TTravelDoucmentResponse[];
    personalDocumnets: TTravelDoucmentResponse[];
    employeeDocuments: TTravelDoucmentResponse[]
    inEmployeeList: boolean
    expensesMadeByMe: TTravelExpenseResponse[]

    stats: {
        totalEmployees: number,
        totalDocuments: number,
        totalEmployeeDocuments: number,
        totalExpenses: number,
        totalAskedExpenseAmount: number,
        totalApprovedExpenseAmount: number,
        totalPendingExpensesToReview: number,
        totalReviewedExpense: number,
    }
}