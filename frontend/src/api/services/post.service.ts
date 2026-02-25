import type { TCreatePostRequest } from "@/types/apiRequestTypes/TCreatePostRequest.type"
import api from "../api"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import  {type TPostMinResponse, type TPostWisthCommentsAndLikeResponse } from "@/types/apiResponseTypes/TPostWisthCommentsAndLikeResponse.type"
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

export const updatePost = (payload:TCreatePostRequest) => {
    const formData = new FormData()
    formData.append("postDetails",new Blob([JSON.stringify(payload.postDetails)], {type:"application/json"}))
    if(payload.attachment!=undefined && payload.attachment != null ){
        formData.append("attachment",payload.attachment)
    }
    return api.put("/api/posts",formData,{
        headers:{
            "Content-Type":"multipart/form-data"
        }
    })
}

export const fetchPosts = ({page,limit,query,postTo,postFrom}:{page?:string,limit?:string,query?:string, postTo?:string,postFrom?:string}) => {
    var queryString = "".concat(page?`page=${page}&`:"").concat(limit?`limit=${limit}&`:"").concat(query?`query=${query}&`:"").concat(postTo?`postTo=${postTo}&`:"").concat(postFrom?`postFrom=${postFrom}&`:"")
    return api.get<TGlobalResponse<TPageableProps<TPostWisthCommentsAndLikeResponse>>>("/api/posts?"+queryString)
}

export const fetchPostUploadedBySelf = ({page,limit,query, postTo,postFrom}:{page?:string,limit?:string,query?:string, postTo?:string,postFrom?:string}) => {
    var queryString = "".concat(page?`page=${page}&`:"").concat(limit?`limit=${limit}&`:"").concat(query?`query=${query}&`:"").concat(postTo?`postTo=${postTo}&`:"").concat(postFrom?`postFrom=${postFrom}&`:"")
    return api.get<TGlobalResponse<TPageableProps<TPostWisthCommentsAndLikeResponse>>>("/api/posts/uploaded-by-self?"+queryString)
}

export const commentOn = (payload:TComment & {postId:string|number}) => {
    return api.post(`api/posts/${payload.postId}/comment`,{
        comment: payload.comment
    })
}
export const updateComment = (payload:TComment) => {
    return api.put(`/api/posts/comment`,payload)
}
export const deleteUnAprropriatePost = (payload:TDeleteUnappropriateContent) => {
    return api.put("/api/posts/delete-unappropriate",payload)
}

export const deleteUnAprropriatePostComment = (payload:TDeleteUnappropriateContent ) => {
    return api.put("/api/posts/comment/delete-unappropriate",payload)

}

export const likeUnlikePost = (postId:number|string)=>{
    return api.post(`/api/posts/${postId}/like-unlike`)
}

export const deletePost = (postId: string | number) => {
    return api.delete(`/api/posts/${postId}`)
}
export const deletePostComment = (commentId: string | number) => {
    return api.delete(`/api/posts/comment/${commentId}`)
}

export const fetchPostById = (postId:number) =>{
    return api.get<TGlobalResponse<TPostMinResponse>>(`/api/posts/${postId}`)
}