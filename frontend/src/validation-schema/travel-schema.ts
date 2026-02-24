import * as z from "zod";
export const travelSchema = z.object({
    id:  z.coerce.number().optional(),
    title: z.string().trim().min(1,{error:"title required"}),
    descripton: z.string().trim().min(1,{error:"title required"}),
    maxReimbursementAmountPerDay: z.coerce.number().positive(),
    startDate: z.coerce.date(),
    endDate: z.coerce.date(),
    lastDateToSubmitExpense: z.coerce.date(),
    employeeIds : z.coerce.number().array().min(1,{error:"atleast select 1 employee"})
})