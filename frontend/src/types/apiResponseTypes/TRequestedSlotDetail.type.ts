import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"

export type TRequestedSlotDetail =
    {
      id: number,
      status: string,
      gameSlot: {
        id: number,
        slotDate: Date,
        startsFrom: string,
        endsAt: string,
        gameTypeId: number,
        gameType: string,
        available: boolean
      },
      requestedBy: TEmployeeWithNameOnly,
      slotRequestWiseEmployee: {employee:TEmployeeWithNameOnly}[]
    }