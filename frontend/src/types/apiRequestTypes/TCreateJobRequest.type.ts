// export type TCreateJobDetail = {
//     id: number,
//     title: string,
//     description: string,
//     workMode: string,
//     skills: string[],
//     vacancy: number,
//     reviewerIds: number[],
//     hrOwnerId: number,
// }
// export type TCreateJobRequest = {
//     jobDetail: TCreateJobDetail,
//     jdDocument: File
// }
// jobCreateSchema
import type { jobCreateSchema } from "@/validation-schema/job-schema";
import * as z from "zod";

export type TCreateJobDetail = z.infer<typeof jobCreateSchema>