import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type"
import type { TCommentResponse } from "./TCommentResponse.type"

export type TPostWisthCommentsAndLikeResponse = {
    id: number
    title: string
    body: string
    attachmentPath: string
    tags: string
    createdBy: TEmployeeMinDetail;
    likeCount: number
    commentCount:number
    recentComments: TCommentResponse[];
    recentLikedBy: TEmployeeMinDetail[];
}