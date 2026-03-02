import { useMutation, useQueryClient } from "@tanstack/react-query";
import { commentOn } from "../services/post.service";
import type { TComment } from "@/types/apiRequestTypes/TCommentRequest.type";

const useCommentMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload: TComment & { postId: number | string }) => commentOn(payload),
            onSuccess: (data) => {
                if(data.data.status === "OK"){
                    queryClient.invalidateQueries({queryKey:["posts"]})
                }   
            }
        }
    )
}
export default useCommentMutation