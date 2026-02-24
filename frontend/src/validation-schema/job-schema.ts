import * as z from "zod";
export const jobCreateSchema = z.object({
  jobDetail:z.object({
    id: z.coerce.number().nullable(),
    title: z.string().trim().min(2, { message: "Title Required" }),
    description: z.string().trim().min(1, { message: "description Required" }),
    workMode: z.string().trim().min(1, { message: "workMode Required" }).nonoptional(),
    skills: z.string().array().min(1, {error:"At least one skill needs to be add"}).max(5),
    vacancy: z.coerce.number().int().positive("vacancy should be positive"),
    reviewerIds: z.coerce.number().array(),
    hrOwnerId: z.coerce.number().positive({error:"Hr owner is required"}).nonoptional("Select hr owner"),
  }),
  jdDocument: z.instanceof(File).optional()
});