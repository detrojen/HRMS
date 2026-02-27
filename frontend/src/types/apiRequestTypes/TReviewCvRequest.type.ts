import type cvReviewSchema from "@/validation-schema/cvReviewSchema";
import * as z from "zod";
export type TReviewCvRequest = z.infer<typeof cvReviewSchema>