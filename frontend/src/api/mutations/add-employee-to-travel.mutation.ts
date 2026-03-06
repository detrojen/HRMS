import { useMutation, useQueryClient } from "@tanstack/react-query"
import { addEmployeeToTravel } from "../services/travel.service"
import type { TAddEmployeeToTravelRequest } from "@/types/apiRequestTypes/TAddEmployeeToTravelRequest.type"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { AxiosResponse } from "axios"
import type { TTravelDetails } from "@/types/apiResponseTypes/TTravelDetails.type"

const useAddEmployeeToTravelMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload:TAddEmployeeToTravelRequest) => addEmployeeToTravel(payload)
            ,onSuccess: (res,payload) => {
                debugger
                if(res.data.status === "OK"){
                    // queryClient.setQueryData(["travel-by-id"+payload.travelId],(oldData:AxiosResponse<TGlobalResponse<TTravelDetails>>)=>{
                    //     const newData = {...oldData}
                    //     newData.data.data = data.data.data
                    // })
                     queryClient.setQueryData(["travel-by-id","travel-by-id"+payload.travelId],(oldData:AxiosResponse<TGlobalResponse<TTravelDetails>>):AxiosResponse<TGlobalResponse<TTravelDetails>>=>({
                        ...oldData
                        ,data:{
                            ...oldData.data,
                            data: {
                                ...oldData.data.data,
                                employees: oldData.data.data.employees.concat(res.data.data)
                            }
                        }
                     }))
                }
            }
        }
    )
}

export default useAddEmployeeToTravelMutation;