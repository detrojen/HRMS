import type { TDeleteExpenseProofRequest } from "@/types/apiRequestTypes/TDeleteExpenseProofRequest"
import { useMutation, useQueryClient } from "@tanstack/react-query"
import { deleteExpenseDocument } from "../services/travel.service"

const useDeleteExpenseProofMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TDeleteExpenseProofRequest) => deleteExpenseDocument(payload),
             onSuccess: (data) => {
                if(data.data.status == "OK"){
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            }
        }
    )
}
export default useDeleteExpenseProofMutation