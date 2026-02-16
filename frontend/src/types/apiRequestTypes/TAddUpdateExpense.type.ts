export type TAddUpdateExpense = {
    expenseDetails:{
        id?:  number
        categoryId?: string
        askedAmout?: number
        dateOfExpense?: Date
        reciept?:string
    },
    file?: File
}