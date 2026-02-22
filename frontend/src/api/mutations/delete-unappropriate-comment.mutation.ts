import { useMutation } from "@tanstack/react-query";
import { deleteUnAprropriatePost, deleteUnAprropriatePostComment } from "../services/post.service";
import type { TDeleteUnappropriateContent } from "@/types/apiRequestTypes/TDeleteUnappropriateContent.type";

const useDeleteUnappropriateCommentMutation = () => {

    return useMutation(
        {
            mutationFn: (payload: TDeleteUnappropriateContent) => deleteUnAprropriatePostComment(payload)
        }
    )
}
export default useDeleteUnappropriateCommentMutation