import type { TQueryGameSlots } from "@/types/apiRequestTypes/TQueryGameSlots.type";
import api from "../api";
import { type TGlobalResponse } from "@/types/TGlobalResponse.type";
import type { TQueryInterestedEmployeeByNameLike } from "@/types/apiRequestTypes/TQueryInterestedEmployeeByNameLike.type";
import { type TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type";
import type { TRequestedSlotDetail } from "@/types/apiResponseTypes/TRequestedSlotDetail.type";
import type { TUpdateGameInterest } from "@/types/apiRequestTypes/TUpdateGameInterest.type";
import type { TGameInterest } from "@/types/apiResponseTypes/TGameInterest.type";
import type { TGameType } from "@/types/apiResponseTypes/TGameType.type";
import type { TCurrentGameStatusResponse } from "@/types/apiResponseTypes/TCurrentGameStatusResponse.type";
import type { TGameSlotResponse } from "@/types/apiResponseTypes/TGameSlotResponse.type";
import type { TSlotRequsetResponse } from "@/types/apiResponseTypes/TSlotRequsetResponse.type";
import type { TCreateUpdateGameTypeRequest } from "@/types/apiRequestTypes/TCreateUpdateGameTypeRequest.type";
import type { TSlotRequestHistoryParams } from "@/types/apiRequestTypes/TSlotRequestHistoryParams.type";
import paramsBuilder from "@/utils/query-parameter-builder";

export const getGameSlotByGameTypeIdOfDate = (payload:TQueryGameSlots)  => {
    return api.get<TGlobalResponse<TGameSlotResponse[]>>(`/api/game-slots/${payload.gameTypeId}?slotDate=${payload.date}`)
}

export const getIntrestedEmployeeByNameLike = (payload:TQueryInterestedEmployeeByNameLike) => {
    return api.get<TGlobalResponse<TEmployeeWithNameOnly[]>>(`/api/employee/interested-game/${payload.gameTypeId}?nameLike=${payload.nameLike.trim()}`)
}

export const requestSlot = (payload:{slotId:number,otherPlayersId:number[]}) => {
    return api.post<TGlobalResponse<TSlotRequsetResponse>>("/api/game-slot/book",payload)
}

export const getActiveSlots = () => {
    return api.get<TGlobalResponse<TSlotRequsetResponse[]>>("/api/game-slots/active")
}

export const getSlotRequestsHistory = (params:TSlotRequestHistoryParams) => {
    const searchParams = paramsBuilder(params);
    return api.get<TGlobalResponse<TSlotRequsetResponse[]>>("/api/game-slots/history"+searchParams);
}

export const cancelRequestedSlot = (slotRequestId:number) => {
    return api.delete<TGlobalResponse<TSlotRequsetResponse>>(`/api/game-slots/cancel/${slotRequestId}`)
}

export const getGameInterests = () => {
    return api.get<TGlobalResponse<TGameInterest[]>>("/api/employee-wise-game-interests")
}

export const getRequestedSlotDetail = (requestId:string) => {
    return api.get<TGlobalResponse<TRequestedSlotDetail>>(`/api/game-slots/requested/${requestId}`)
}

export const updateGameInterest = (payload: TUpdateGameInterest) => {
    return api.put<TGlobalResponse<TGameInterest>>('/api/game-type/update-interest',payload)
}

export const getAllGameTypes = () => {
    return api.get<TGlobalResponse<TGameType[]>>('/api/game-types')
}
export const getGameTypeById = (gameTypeId:string) => {
    return api.get<TGlobalResponse<TGameType>>(`/api/game-types/${gameTypeId}`)
}

export const createGameType = (payload:TCreateUpdateGameTypeRequest) => {
    
    return api.post("/api/game-types", payload)
}

export const updateGameType =  (payload:TCreateUpdateGameTypeRequest) => {
    
    return  api.put<TGlobalResponse<TGameType>>(`/api/game-types`,payload)
}

export const getCurrentGameStatus = () => {
    return api.get<TGlobalResponse<TCurrentGameStatusResponse[]>>("/api/game/current")
}