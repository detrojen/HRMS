import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type"
import type { TGameSlotResponse } from "./TGameSlotResponse.type"
import type { TRequestedSlotDetail } from "./TRequestedSlotDetail.type"

export type TCurrentGameStatusResponse = {
    players: TEmployeeMinDetail[],
    gameSlot: TGameSlotResponse
}