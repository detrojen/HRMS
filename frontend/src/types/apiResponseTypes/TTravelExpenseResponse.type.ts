import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"
import type { TCategory } from "./TCategory.type"

export type TTravelExpenseResponse = {
    id:  number
    description:string
    category: TCategory
    askedAmount: number
    reciept: string
    dateOfExpense: Date
    employee: TEmployeeWithNameOnly
    reviewedBy:  TEmployeeWithNameOnly
    aprrovedAmount: number
    remark: String
    status: "approved" | "pending" | "rejected"
}