import { useMutation, useQueryClient } from "@tanstack/react-query"
import { cancelTravelById } from "../services/travel.service"
import type { AxiosResponse } from "axios"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { TTravelDetails } from "@/types/apiResponseTypes/TTravelDetails.type"
import type { TTravelMinDetail } from "@/types/apiResponseTypes/TTravelMinDetails.type"

const useCancelTravleMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
           mutationFn: (travelId: number) => cancelTravelById(travelId)
            ,onSuccess: (res,travelId) => {
                debugger
                if(res.data.status === "OK"){
                    
                     queryClient.setQueryData(["travel-by-id","travel-by-id"+travelId],(oldData:AxiosResponse<TGlobalResponse<TTravelDetails>>):AxiosResponse<TGlobalResponse<TTravelDetails>>=>({
                        ...oldData
                        ,data:{
                            ...oldData.data,
                            data: {
                                ...oldData.data.data,
                                status: "CANCELLED"
                            }
                        }
                     }))
                     queryClient.setQueryData(["travels"],(oldData:AxiosResponse<TGlobalResponse<TTravelMinDetail[]>>):AxiosResponse<TGlobalResponse<TTravelMinDetail[]>>=>({
                        ...oldData
                        ,data:{
                            ...oldData.data,
                            data: oldData.data.data.map(item=>(item.id!=travelId? {...item} : {...item,status:"CANCELLED"}))
                        }
                     }))
                     
                }
            }
        }
    )
}
export default useCancelTravleMutation