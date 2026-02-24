import { useMutation, useQueryClient } from "@tanstack/react-query";
import {  updateExpense } from "../services/travel.service";
import type { TAddUpdateExpense } from "@/types/apiRequestTypes/TAddUpdateExpense.type";

const useUpdateExpenseMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TAddUpdateExpense & {travelId:string | number}) => updateExpense(payload),
             onSuccess: (data) => {
                if(data.status == "OK"){
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            }
        }
    )
}
export default useUpdateExpenseMutation