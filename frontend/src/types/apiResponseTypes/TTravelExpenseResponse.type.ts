import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"
import type { TCategory } from "./TCategory.type"
import type { TExpenseDocument } from "./TExpenseDocument.type"

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
    remark: string
    status: "approved" | "pending" | "rejected"
    proofs: TExpenseDocument[]
}