import { useQuery } from "@tanstack/react-query"
import { getActiveSlots, getGameSlotByGameTypeIdOfDate, getIntrestedEmployeeByNameLike } from "../services/gameScheduling.service"
import type { TQueryGameSlots } from "@/types/apiRequestTypes/TQueryGameSlots.type"
import type { TGlobalResponse } from "@/types/apiResponseTypes/TGlobalResponse.type"
import type { TQueryInterestedEmployeeByNameLike } from "@/types/apiRequestTypes/TQueryInterestedEmployeeByNameLike.type"

export const useQueryGameSlots = (data:TQueryGameSlots)=>{

    return useQuery<TGlobalResponse<any>>({
        queryKey: ["game-slots"],
        queryFn: ()=> getGameSlotByGameTypeIdOfDate(data),
    })
}

export const useFetchInterestedEmployeeByNameLike = (payload:TQueryInterestedEmployeeByNameLike) => {
    return useQuery(
        {
            queryKey: ["interested-employees",payload.nameLike],
            queryFn: ()=>getIntrestedEmployeeByNameLike(payload)
        }
    )
}

export const useFetchActiveSlots = () => {
    return useQuery(
        {
            queryKey:["active-slots"],
            queryFn: ()=>getActiveSlots()
        }
    )
}
