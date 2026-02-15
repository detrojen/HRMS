export type TCreateTravelRequest = {
    id:  number
    title: string,
    descripton: string
    maxReimbursementAmountPerDay: number
    startDate: Date
    endDate: Date
    lastDateToSubmitExpense: Date
    employeeIds : number[];
}