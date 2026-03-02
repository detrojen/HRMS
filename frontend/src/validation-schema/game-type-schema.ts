import z from "zod";

export const gameTypeSchema = z.object({
    game: z.string().trim().min(1, { error: "game required" }),
    slotDuration: z.coerce.number<number>().positive(),
    openingHours: z.codec(z.iso.time(),z.string(), {
        decode: async (time) => time.toString(),
        encode: async (str) => new Date(str).getTime().toString(),
    }),
    closingHours: z.codec(z.iso.time(),z.string(), {
        decode: async (time) => time.toString(),
        encode: async (str) => new Date(str).getTime().toString(),
    }),
    maxNoOfPlayers: z.coerce.number<number>().positive(),
    slotCanBeBookedBefore: z.coerce.number<number>().positive(),
    maxSlotPerDay: z.coerce.number<number>().positive(),
    maxActiveSlotPerDay: z.coerce.number<number>().positive(),
    id: z.coerce.number<number>().positive().optional(),
    inMaintenance: z.boolean()
})