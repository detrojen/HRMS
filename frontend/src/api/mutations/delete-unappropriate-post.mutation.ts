import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteUnAprropriatePost } from "../services/post.service";
import type { TDeleteUnappropriateContent } from "@/types/apiRequestTypes/TDeleteUnappropriateContent.type";

const useDeleteUnappropriatePostMutation = () => {
    const queryClient = useQueryClient()

    return useMutation(
        {
            mutationFn: (payload: TDeleteUnappropriateContent) => deleteUnAprropriatePost(payload)
            , onSuccess: (data) => {

                if (data.status === "OK") {
                    queryClient.invalidateQueries({ queryKey: ["posts"] })
                }
            }
        }
    )
}
export default useDeleteUnappropriatePostMutation