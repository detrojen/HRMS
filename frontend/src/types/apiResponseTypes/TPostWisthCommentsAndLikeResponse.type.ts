import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type"
import type { TCommentResponse } from "./TCommentResponse.type"
export type TPostMinResponse = {
id: number
    title: string
    body: string
    attachmentPath: string
    tags: string
    createdBy: TEmployeeMinDetail;
    likeCount: number
    commentCount:number
}
export type TPostWisthCommentsAndLikeResponse = TPostMinResponse & {
    recentComments: TCommentResponse[];
    recentLikedBy: TEmployeeMinDetail[];
}