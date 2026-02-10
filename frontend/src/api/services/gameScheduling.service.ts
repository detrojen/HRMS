import type { TQueryGameSlots } from "@/types/apiRequestTypes/TQueryGameSlots.type";
import api from "../api";
import { type TGlobalResponse } from "@/types/apiResponseTypes/TGlobalResponse.type";
import type { TQueryInterestedEmployeeByNameLike } from "@/types/apiRequestTypes/TQueryInterestedEmployeeByNameLike.type";
import { type TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type";

export const getGameSlotByGameTypeIdOfDate = (payload:TQueryGameSlots)  => {
    return api.get<TGlobalResponse<any>>(`/api/game-slots/${payload.gameTypeId}?slotDate=${payload.date}`).then(res=>res.data)
}

export const getIntrestedEmployeeByNameLike = (payload:TQueryInterestedEmployeeByNameLike) => {
    return payload.nameLike.trim() == "" ? {data : [],
     status : 0,
     message : null,
     authToken : null,
     errors : null}:api.get<TGlobalResponse<TEmployeeWithNameOnly[]>>(`/api/employee/interested-game/${payload.gameTypeId}?nameLike=${payload.nameLike.trim()}`).then(res=>res.data)
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