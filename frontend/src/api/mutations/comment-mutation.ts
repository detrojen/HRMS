import { useMutation } from "@tanstack/react-query";
import { commentOn } from "../services/post.service";
import type { TComment } from "@/types/apiRequestTypes/TCommentRequest.type";

const useCommentMutation = () => {
    return useMutation(
        {
            mutationFn: (payload: TComment & { postId: number | string }) => commentOn(payload),
        }
    )
}
export default useCommentMutation