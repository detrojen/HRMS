import type { gameTypeSchema } from "@/validation-schema/game-type-schema";
import * as z from "zod"
export type TCreateUpdateGameTypeRequest = z.infer<typeof gameTypeSchema>