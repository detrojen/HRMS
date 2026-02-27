import * as z from "zod"
const cvReviewSchema = z.object(
    {
        review: z.string().trim().min(1, { error: "review mandatory" })
    }
)

export default cvReviewSchema