import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"
import type { TGameSlotResponse } from "./TGameSlotResponse.type"

export type TRequestedSlotDetail =
    {
      id: number,
      status: string,
      gameSlot: TGameSlotResponse,
      requestedBy: TEmployeeWithNameOnly,
      slotRequestWiseEmployee: {employee:TEmployeeWithNameOnly}[]
    }