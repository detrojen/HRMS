import { useMutation, useQueryClient } from "@tanstack/react-query"
import {  deleteTravelDocument } from "../services/travel.service"
import type { TTDeleteTravelDocumnetRequest } from "@/types/apiRequestTypes/TTDeleteTravelDocumnetRequest.type"

const useDeleteTravelDocumnetMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TTDeleteTravelDocumnetRequest) => deleteTravelDocument(payload),
             onSuccess: (data) => {
                if(data.data.status == "OK"){
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            }
        }
    )
}

export default useDeleteTravelDocumnetMutation