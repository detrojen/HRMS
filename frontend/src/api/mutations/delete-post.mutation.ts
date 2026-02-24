import { useMutation, useQueryClient } from "@tanstack/react-query"
import { deletePost } from "../services/post.service"

const useDeletePostMutation = () => {
    const queryclient = useQueryClient()
    return useMutation({
        mutationFn: (postId: number|string) => deletePost(postId),
        onSuccess:(data)=>{
            
            if (data.status === "OK"){
                queryclient.invalidateQueries({queryKey:["posts"]})
            }
        }
    })
}

export default useDeletePostMutation