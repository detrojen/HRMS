import type { TCreatePostRequest } from "@/types/apiRequestTypes/TCreatePostRequest.type"
import api from "../api"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { TPostWisthCommentsAndLikeResponse } from "@/types/apiResponseTypes/TPostWisthCommentsAndLikeResponse.type"
import type { TComment } from "@/types/apiRequestTypes/TCommentRequest.type"
import type { TDeleteUnappropriateContent } from "@/types/apiRequestTypes/TDeleteUnappropriateContent.type"
import type { TPageableProps } from "@/types/propsTypes/TPageableProps.type"

export const createPost = (payload:TCreatePostRequest) => {
    const formData = new FormData()
    formData.append("postDetails",new Blob([JSON.stringify(payload.postDetails)], {type:"application/json"}))
    formData.append("attachment",payload.attachment)
    return api.post("/api/posts",formData,{
        headers:{
            "Content-Type":"multipart/form-data"
        }
    })
}

export const fetchPosts = ({page,limit,query}:{page?:number,limit?:number,query?:string}) => {
    var queryString = "".concat(page?`page=${page}&`:"").concat(limit?`limit=${limit}&`:"").concat(query?`query=${query}&`:"")
    return api.get<TGlobalResponse<TPageableProps<TPostWisthCommentsAndLikeResponse>>>("/api/posts?"+queryString)
}

export const commentOn = (payload:TComment & {postId:string|number}) => {
    return api.post(`api/posts/${payload.postId}/comment`,{
        comment: payload.comment
    })
}

export const deleteUnAprropriatePost = (payload:TDeleteUnappropriateContent) => {
    return api.put("/api/posts/delete-unappropriate",payload)
}