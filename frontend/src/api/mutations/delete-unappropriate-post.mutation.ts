import { useMutation } from "@tanstack/react-query";
import { deleteUnAprropriatePost } from "../services/post.service";
import type { TDeleteUnappropriateContent } from "@/types/apiRequestTypes/TDeleteUnappropriateContent.type";

const useDeleteUnappropriatePostMutation = () => {

    return useMutation(
        {
            mutationFn: (payload: TDeleteUnappropriateContent) => deleteUnAprropriatePost(payload)
        }
    )
}
export default useDeleteUnappropriatePostMutation