import type { expenseSchema } from "@/validation-schema/expense-schema";
import * as z from "zod";

export type TAddUpdateExpense = z.infer<typeof expenseSchema>