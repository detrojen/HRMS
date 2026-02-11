export type TGameType = {
    game: string,
    slotDuration: number,
    openingHours: Date,
    closingHours: Date,
    maxNoOfPlayers: number,
    slotCanBeBookedBefore: number,
    maxSlotPerDay: number,
    maxActiveSlotPerDay: number,
    id: number,
    noOfInteretedEmployees: number,
    inMaintenance: boolean
}
