import { useMutation, useQueryClient } from "@tanstack/react-query";
import { addExpense } from "../services/travel.service";
import type { TAddUpdateExpense } from "@/types/apiRequestTypes/TAddUpdateExpense.type";

const useAddExpenseMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TAddUpdateExpense & {travelId:string | number}) => addExpense(payload),
            onSuccess: (data) => {
                if(data.status == "OK"){
                    
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            }
        }
    )
}
export default useAddExpenseMutation