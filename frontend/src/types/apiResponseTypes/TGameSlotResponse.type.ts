export type TGameSlotResponse = {
        id: number,
        slotDate: Date,
        startsFrom: string,
        endsAt: string,
        gameTypeId: number,
        gameType: string,
        available: boolean
      }