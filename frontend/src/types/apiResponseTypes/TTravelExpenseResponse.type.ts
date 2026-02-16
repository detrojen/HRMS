import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"
import type { TCategory } from "./TCategory.type"

export type TTravelExpenseResponse = {
    id:  number
    category: TCategory
    askedAmout: number
    reciept: string
    dateOfExpense: Date
    employee: TEmployeeWithNameOnly
    reviedBy:  TEmployeeWithNameOnly
    aprrovedAmout: number
    remark: String
}