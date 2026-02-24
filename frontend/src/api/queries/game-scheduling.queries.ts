import { useQuery } from "@tanstack/react-query"
import { getActiveSlots, getAllGameTypes, getCurrentGameStatus, getGameInterests, getGameSlotByGameTypeIdOfDate, getGameTypeById, getIntrestedEmployeeByNameLike, getRequestedSlotDetail } from "../services/game-scheduling.service"
import type { TQueryGameSlots } from "@/types/apiRequestTypes/TQueryGameSlots.type"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { TQueryInterestedEmployeeByNameLike } from "@/types/apiRequestTypes/TQueryInterestedEmployeeByNameLike.type"
import useSyncDataStore from "@/hooks/use-sync-data-store"
import { setInitialData } from "@/store/slices/game-interest-slice"
import type { TGameInterest } from "@/types/apiResponseTypes/TGameInterest.type"
import type { TRequestedSlotDetail } from "@/types/apiResponseTypes/TRequestedSlotDetail.type"
import type { TGameType } from "@/types/apiResponseTypes/TGameType.type"

export const useQueryGameSlots = (data:TQueryGameSlots)=>{

    return useQuery<TGlobalResponse<any>>({
        queryKey: ["game-slots",`game-slot-id-${data.gameTypeId}-${data.date}`],
        queryFn: ()=> getGameSlotByGameTypeIdOfDate(data),
        staleTime:0
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

export const useFetchGameInterests = ()=> useSyncDataStore<TGlobalResponse<TGameInterest[]>>(
        {
            queryKey: ["game-interest"],
            queryFn:() => getGameInterests(),
            action: setInitialData
        }
)

export const useFetctRequestedSlotDetail = (requestId:string) => {
    return useQuery<TGlobalResponse<TRequestedSlotDetail>>(
        {
            queryKey:["requested-slot-detail", `requested-slot-detial-${requestId}`],
            queryFn:()=>getRequestedSlotDetail(requestId)
        }
    )
}

export const useFetchGameTypes = () => {
    return useQuery<TGameType[]>({
        queryKey:["game-types"],
        queryFn: () => getAllGameTypes()
    })
}
export const useFetchGameTypeById = (gameTypeId:string) => {
    return useQuery<TGameType>({
        queryKey:["game-types",`game-type-${gameTypeId}`],
        queryFn: () => getGameTypeById(gameTypeId)
    })
}


export const useFetchCurrentGameStatus = () => {
    return useQuery({
        queryKey:["current-game-status"],
        queryFn: () => getCurrentGameStatus()
    })
}

