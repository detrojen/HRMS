import * as z from "zod";
export const travelSchema = z.object({
    id:  z.coerce.number().optional(),
    title: z.string().trim().min(1,{error:"title required"}),
    descripton: z.string().trim().min(1,{error:"title required"}),
    maxReimbursementAmountPerDay: z.coerce.number().positive({error: "smount should be greater than 0"}),
    startDate: z.coerce.date({error: "start date is required"}),
    endDate: z.coerce.date({error: "end date is required"}),
    lastDateToSubmitExpense: z.coerce.date({error: "last date to submit expense is required"}),
    employeeIds : z.coerce.number().array()
})