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

export const getGameSlotByGameTypeIdOfDate = (payload:TQueryGameSlots)  => {
    return api.get<TGlobalResponse<any>>(`/api/game-slots/${payload.gameTypeId}?slotDate=${payload.date}`).then(res=>res.data)
}

export const getIntrestedEmployeeByNameLike = (payload:TQueryInterestedEmployeeByNameLike) => {
    return payload.nameLike.trim() == "" ? []:api.get<TGlobalResponse<TEmployeeWithNameOnly[]>>(`/api/employee/interested-game/${payload.gameTypeId}?nameLike=${payload.nameLike.trim()}`).then(res=>res.data)
}

export const requestSlot = (payload:{slotId:number,otherPlayersId:number[]}) => {
    return api.post("/api/game-slot/book",payload).then(res=>res.data); 
}

export const getActiveSlots = () => {
    return api.get("/api/game-slots/active").then(res=>res.data)
}

export const cancelRequestedSlot = (slotRequestId:number) => {
    return api.delete(`/api/game-slots/cancel/${slotRequestId}`).then(res=>res.data)
}

export const getGameInterests = () => {
    return api.get("/api/employee-wise-game-interests").then(res=>res.data)
}

export const getRequestedSlotDetail = (requestId:string) => {
    return api.get<TGlobalResponse<TRequestedSlotDetail>>(`/api/game-slots/requested/${requestId}`).then(res=>res.data)
}

export const updateGameInterest = (payload: TUpdateGameInterest) => {
    return api.put<TGlobalResponse<TGameInterest>>('/api/game-type/update-interest',payload)
}

export const getAllGameTypes = () => {
    return api.get<TGameType[]>('/api/game-types').then(res=>res.data)
}
export const getGameTypeById = (gameTypeId:string) => {
    return api.get<TGameType>(`/api/game-types/${gameTypeId}`).then(res=>res.data)
}

export const createGameType = (payload:Omit<TGameType,"id">) => {
    
    return api.post("/api/game-types", payload).then(res=>res.data)
}

export const updateGameType =  (payload:any) => {
    
    return  api.put<TGameType>(`/api/game-types`,payload).then(res=>res.data)
}

export const getCurrentGameStatus = () => {
    return api.get<TGlobalResponse<TCurrentGameStatusResponse[]>>("/api/game/current")
}