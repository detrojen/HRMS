import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteUnAprropriatePost, deleteUnAprropriatePostComment } from "../services/post.service";
import type { TDeleteUnappropriateContent } from "@/types/apiRequestTypes/TDeleteUnappropriateContent.type";

const useDeleteUnappropriateCommentMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload: TDeleteUnappropriateContent) => deleteUnAprropriatePostComment(payload)
            , onSuccess: (data) => {

                if (data.status === "OK") {
                    queryClient.invalidateQueries({ queryKey: ["posts"] })
                }
            }
        }
    )
}
export default useDeleteUnappropriateCommentMutation