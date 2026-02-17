export type TAddUpdateExpense = {
    expenseDetails:{
        id?:  number
        description?:string
        categoryId?: string
        askedAmount?: number
        dateOfExpense?: Date
        reciept?:string
    },
    file?: File
}