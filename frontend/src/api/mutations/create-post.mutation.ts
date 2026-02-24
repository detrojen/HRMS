import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { createPost } from "../services/post.service";
import type { TCreatePostRequest } from "@/types/apiRequestTypes/TCreatePostRequest.type";

const useCreatePostMutation = () => {
    const navTo = useNavigate();
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload: TCreatePostRequest) => createPost(payload),
        onSuccess:(data)=>{
            if(data.data.status == "OK"){
                navTo("/posts")
                queryClient.invalidateQueries({queryKey:["posts"]})
            }
        },
        
    }
) 
}
export default useCreatePostMutation