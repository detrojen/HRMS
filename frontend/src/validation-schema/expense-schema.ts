import * as z from "zod";
export const  expenseSchema = z.object({
    expenseDetails:z.object({
        id:  z.coerce.number().optional(),
        description:z.string().trim().min(1,{error:"Description required"}),
        categoryId: z.coerce.number().positive({error:"category is required"}),
        askedAmount: z.coerce.number().positive().int(),
        dateOfExpense: z.coerce.date(),
        reciept:z.string().optional()
    }),
    file: z.instanceof(File).optional()
})