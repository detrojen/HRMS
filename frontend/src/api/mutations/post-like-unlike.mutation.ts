import { useMutation } from "@tanstack/react-query"
import { likeUnlikePost } from "../services/post.service"

const usePostLikeUnlikeMutation = () => {
    return useMutation({
            mutationFn: (postId: number|string) => likeUnlikePost(postId)
    })
}

export default usePostLikeUnlikeMutation