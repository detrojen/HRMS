import { useMutation, useQueryClient } from "@tanstack/react-query"
import {  deletePostComment } from "../services/post.service"

const useDeletePostCommentMutation = () => {
    const queryclient = useQueryClient()
    return useMutation({
        mutationFn: (commentId: number|string) => deletePostComment(commentId),
        onSuccess:(data)=>{
            debugger
            if (data.status === "OK"){
                queryclient.invalidateQueries({queryKey:["posts"]})
            }
        }
    })
}

export default useDeletePostCommentMutation