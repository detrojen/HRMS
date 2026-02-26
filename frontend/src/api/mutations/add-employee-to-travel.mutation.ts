import { useMutation, useQueryClient } from "@tanstack/react-query"
import { addEmployeeToTravel } from "../services/travel.service"
import type { TAddEmployeeToTravelRequest } from "@/types/apiRequestTypes/TAddEmployeeToTravelRequest.type"

const useAddEmployeeToTravelMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TAddEmployeeToTravelRequest) => addEmployeeToTravel(payload)
            ,onSuccess: (data) => {
                if(data.status === "OK"){
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            }
        }
    )
}

export default useAddEmployeeToTravelMutation;