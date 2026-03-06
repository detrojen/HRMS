import { useQuery } from "@tanstack/react-query"
import { getActiveSlots, getAllGameTypes, getCurrentGameStatus, getGameInterests, getGameSlotByGameTypeIdOfDate, getGameTypeById, getIntrestedEmployeeByNameLike, getRequestedSlotDetail, getSlotRequestsHistory } from "../services/game-scheduling.service"
import type { TQueryGameSlots } from "@/types/apiRequestTypes/TQueryGameSlots.type"
import type { TQueryInterestedEmployeeByNameLike } from "@/types/apiRequestTypes/TQueryInterestedEmployeeByNameLike.type"
import type { TSlotRequestHistoryParams } from "@/types/apiRequestTypes/TSlotRequestHistoryParams.type"


export const useQueryGameSlots = (data: TQueryGameSlots) => {

    return useQuery({
        queryKey: ["game-slots", `game-slot-id-${data.gameTypeId}-${data.date}`],
        queryFn: () => getGameSlotByGameTypeIdOfDate(data),
        staleTime: 0
    })
}

export const useFetchInterestedEmployeeByNameLike = (payload: TQueryInterestedEmployeeByNameLike) => {
    return useQuery(
        {
            queryKey: ["interested-employees", payload.nameLike],
            queryFn: () => getIntrestedEmployeeByNameLike(payload)
        }
    )
}

export const useFetchActiveSlots = () => {
    return useQuery(
        {
            queryKey: ["active-slots"],
            queryFn: () => getActiveSlots()
        }
    )
}
export const useFetchSlotRequests = (params:TSlotRequestHistoryParams) => {
    return useQuery(
        {
            queryKey: ["slot-request-history", params],
            queryFn: () => getSlotRequestsHistory(params)
        }
    )
}

export const useFetchGameInterests = () => {
    

    return useQuery(
        {
            queryKey: ["game-interest"],
            queryFn: () => getGameInterests(),
            
        }
    )
}

export const useFetctRequestedSlotDetail = (requestId: string) => {
    return useQuery(
        {
            queryKey: ["requested-slot-detail", `requested-slot-detial-${requestId}`],
            queryFn: () => getRequestedSlotDetail(requestId)
        }
    )
}

export const useFetchGameTypes = () => {
    return useQuery({
        queryKey: ["game-types"],
        queryFn: () => getAllGameTypes()
    })
}
export const useFetchGameTypeById = (gameTypeId: string) => {
    return useQuery({
        queryKey: ["game-types", `game-type-${gameTypeId}`],
        queryFn: () => getGameTypeById(gameTypeId)
    })
}


export const useFetchCurrentGameStatus = () => {
    return useQuery({
        queryKey: ["current-game-status"],
        queryFn: () => getCurrentGameStatus()
    })
}

