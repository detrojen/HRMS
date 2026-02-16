import { useMutation } from "@tanstack/react-query";
import {  updateExpense } from "../services/travel.service";
import type { TAddUpdateExpense } from "@/types/apiRequestTypes/TAddUpdateExpense.type";

const useUpdateExpenseMutation = () => {
    return useMutation(
        {
            mutationFn: (payload:TAddUpdateExpense & {travelId:string | number}) => updateExpense(payload),
        }
    )
}
export default useUpdateExpenseMutation