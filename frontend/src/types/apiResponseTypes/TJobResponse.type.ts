import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type";
import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type";

export type TJobResponse = {
    id: number,
    title: string,
    description: string,
    workMode: string,
    vacancy: number,
    skills: string
    status: string
    hrOwner: TEmployeeWithNameOnly
    reviewers: {
        id: number,
        reviewer: TEmployeeMinDetail
    }[]
}