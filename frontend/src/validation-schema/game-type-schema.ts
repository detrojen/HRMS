import z from "zod";

export const gameTypeSchema = z.object({
    game: z.string().trim().min(1, { error: "game required" }),
    slotDuration: z.coerce.number().positive(),
    openingHours: z.codec(z.iso.time(),z.string(), {
        decode: async (time) => time.toString(),
        encode: async (str) => new Date(str).getTime().toString(),
    }),
    closingHours: z.codec(z.iso.time(),z.string(), {
        decode: async (time) => time.toString(),
        encode: async (str) => new Date(str).getTime().toString(),
    }),
    maxNoOfPlayers: z.coerce.number().positive(),
    slotCanBeBookedBefore: z.coerce.number().positive(),
    maxSlotPerDay: z.coerce.number().positive(),
    maxActiveSlotPerDay: z.coerce.number().positive(),
    id: z.coerce.number().positive().optional(),
    inMaintenance: z.boolean().default(false)
})

type typeSchema = z.infer<typeof gameTypeSchema>