import type { TCreateJobDetail } from "../apiRequestTypes/TCreateJobRequest.type";
import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type";
import type { TCvReviewerWithNameOnly } from "./TCvReviewerWithNameOnly";

export type TJobResponse = {
    id: number,
    title: string,
    description: string,
    workMode: string,
    vacancy: number,
    skills: string
    status: string
    hrOwner: TEmployeeWithNameOnly
    reviewers: TCvReviewerWithNameOnly[]
}