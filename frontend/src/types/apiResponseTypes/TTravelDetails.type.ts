import type { Locale } from "date-fns";
import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type";
import type { TTravelDoucmentResponse } from "./TTravelDocumnetResponse.type";
import type { TTravelExpenseResponse } from "./TTravelExpenseResponse.type";

export type TTravelDetails = {
    title: string
    descripton: string
    maxReimbursementAmountPerDay: number
    startDate: string
    endDate: string
    lastDateToSubmitExpense: string
    employees : TEmployeeWithNameOnly[];
    initiatedBy: TEmployeeWithNameOnly;
    travelDocuments: TTravelDoucmentResponse[];
    personalDocumnets:TTravelDoucmentResponse[];
    employeeDocuments: TTravelDoucmentResponse[]
    inEmployeeList:boolean
    expenses: TTravelExpenseResponse[]
}