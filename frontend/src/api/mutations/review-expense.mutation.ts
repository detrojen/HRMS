import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { TReviewExpenseRequest } from "@/types/apiRequestTypes/TReviewExpenesRequest.type";
import { reviewExpense } from "../services/travel.service";

const useReviewExpenseMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TReviewExpenseRequest ) => reviewExpense(payload),
            onSuccess: (data) => {
                queryClient.invalidateQueries({queryKey:["travel-expense"]})
            }
        }
    )
}
export default useReviewExpenseMutation