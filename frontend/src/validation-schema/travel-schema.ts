import * as z from "zod";
export const travelSchema = z.object({
    id:  z.coerce.number<number>().optional(),
    title: z.string().trim().min(1,{error:"title required"}),
    descripton: z.string().trim().min(1,{error:"title required"}),
    maxReimbursementAmountPerDay: z.coerce.number<number>().positive({error: "smount should be greater than 0"}),
    startDate: z.coerce.date<Date>({error: "start date is required"}),
    endDate: z.coerce.date<Date>({error: "end date is required"}),
    lastDateToSubmitExpense: z.coerce.date<Date>({error: "last date to submit expense is required"}),
    employeeIds : z.coerce.number<number>().array()
})