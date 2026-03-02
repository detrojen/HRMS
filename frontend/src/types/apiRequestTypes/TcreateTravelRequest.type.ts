import type { travelSchema } from "@/validation-schema/travel-schema";
import * as z from "zod"
export type TCreateTravelRequest = z.infer<typeof travelSchema>