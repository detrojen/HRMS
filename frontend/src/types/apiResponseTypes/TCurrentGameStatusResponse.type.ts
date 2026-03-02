import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type"
import type { TGameSlotResponse } from "./TGameSlotResponse.type"
export type TCurrentGameStatusResponse = {
    players: TEmployeeMinDetail[],
    gameSlot: TGameSlotResponse
}