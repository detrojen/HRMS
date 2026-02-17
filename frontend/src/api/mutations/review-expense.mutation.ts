import { useMutation } from "@tanstack/react-query";
import type { TReviewExpenseRequest } from "@/types/apiRequestTypes/TReviewExpenesRequest.type";
import { reviewExpense } from "../services/travel.service";

const useReviewExpenseMutation = () => {
    return useMutation(
        {
            mutationFn: (payload:TReviewExpenseRequest ) => reviewExpense(payload),
        }
    )
}
export default useReviewExpenseMutation