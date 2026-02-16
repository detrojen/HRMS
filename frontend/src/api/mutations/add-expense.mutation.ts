import { useMutation } from "@tanstack/react-query";
import { addExpense } from "../services/travel.service";
import type { TAddUpdateExpense } from "@/types/apiRequestTypes/TAddUpdateExpense.type";

const useAddExpenseMutation = () => {
    return useMutation(
        {
            mutationFn: (payload:TAddUpdateExpense & {travelId:string | number}) => addExpense(payload),
        }
    )
}
export default useAddExpenseMutation