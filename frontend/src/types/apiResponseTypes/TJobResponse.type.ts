import type { TCreateJobDetail } from "../apiRequestTypes/TCreateJobRequest.type";
import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type";
import type { TCvReviewerWithNameOnly } from "./TCvReviewerWithNameOnly";

export type TJobResponse = Omit<Omit<Omit<TCreateJobDetail,"hrOwnerId">,"reviewerIds">,"skills"> & {
    skills: string
    status: string
    hrOwner: TEmployeeWithNameOnly
    reviewers: TCvReviewerWithNameOnly[]
}