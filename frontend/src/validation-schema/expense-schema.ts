import * as z from "zod";
export const expenseSchema = z.object({
    expenseDetails: z.object({
        id: z.coerce.number<number>().optional(),
        description: z.string().trim().min(1, { error: "Description required" }),
        categoryId: z.coerce.number<number>().positive({ error: "category is required" }),
        askedAmount: z.coerce.number<number>().positive({ error: "axpense amount should be positive" }),
        dateOfExpense: z.coerce.date<Date>(),
        reciept: z.string().optional()
    }),
    file: z.instanceof(FileList).optional()
}).refine(data => {
    debugger
    return (data.expenseDetails.id == undefined && data.file != undefined)

}, {
    error:"At least one should be select to submit expense",path:["description"]
})