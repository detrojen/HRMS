import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type"

export type TCommentResponse = {
    id: number
    comment: string
    commentedBy: TEmployeeMinDetail
}