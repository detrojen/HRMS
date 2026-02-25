import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"

export type TTravelMinDetail = {
    id: number
    title: string
    descripton: string
    maxReimbursementAmountPerDay: number
    startDate: Date
    endDate: Date
    lastDateToSubmitExpense: Date
    initiatedBy: TEmployeeWithNameOnly
}