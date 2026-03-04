import type { TTDeleteTravelDocumnetRequest } from "@/types/apiRequestTypes/TTDeleteTravelDocumnetRequest.type"
import { useMutation, useQueryClient } from "@tanstack/react-query"
import { deleteTravelEmployeeDocument } from "../services/travel.service"

const useDeleteTravelEmployeeDocumnetMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TTDeleteTravelDocumnetRequest) => deleteTravelEmployeeDocument(payload),
             onSuccess: (data) => {
                if(data.data.status == "OK"){
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            }
        }
    )
}

export default useDeleteTravelEmployeeDocumnetMutation