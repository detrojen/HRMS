import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type"
import type { TJobResponse } from "./TJobResponse.type"

export type TJobApplicationResponse = {
    id: number
    applicantsName: string
    applicantsEmail: string
    applicantsPhone: string
    details: string
    cvPath: string
    referedBy: TEmployeeMinDetail
    job: TJobResponse
}