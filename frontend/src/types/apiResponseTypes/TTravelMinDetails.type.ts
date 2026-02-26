import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"

export type TTravelMinDetail = {
    id: number
    title: string
    descripton: string
    maxReimbursementAmountPerDay: number
    startDate: string
    endDate: string
    lastDateToSubmitExpense: string
    initiatedBy: TEmployeeWithNameOnly
}