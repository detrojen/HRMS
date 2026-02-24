import { useMutation, useQueryClient } from "@tanstack/react-query";
import {  updateComment } from "../services/post.service";
import type { TComment } from "@/types/apiRequestTypes/TCommentRequest.type";

const useUpdateCommentMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload: TComment) => updateComment(payload),
            onSuccess: (data) => {
                if(data.status === "OK"){
                    queryClient.invalidateQueries({queryKey:["posts"]})
                }   
            }
        }
    )
}
export default useUpdateCommentMutation