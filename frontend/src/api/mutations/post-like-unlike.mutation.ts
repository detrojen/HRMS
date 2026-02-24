import { useMutation, useQueryClient } from "@tanstack/react-query"
import { likeUnlikePost } from "../services/post.service"

const usePostLikeUnlikeMutation = () => {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: (postId: number | string) => likeUnlikePost(postId),
        onSuccess: (data) => {
            if (data.status === "OK") {
                queryClient.invalidateQueries({
                    queryKey: ["posts"]
                })
            }

        }
    })
}

export default usePostLikeUnlikeMutation