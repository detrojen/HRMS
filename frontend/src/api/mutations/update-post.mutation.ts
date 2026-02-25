import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import {  updatePost } from "../services/post.service";
import type { TCreatePostRequest } from "@/types/apiRequestTypes/TCreatePostRequest.type";

const useUpdatePostMutation = () => {
    const navTo = useNavigate();
    const queryClient = useQueryClient();
    return useMutation(
    {
        mutationFn: (payload: TCreatePostRequest) => updatePost(payload),
        onSuccess:(data)=>{
            if(data.status == "OK"){
                navTo("/posts")
                queryClient.invalidateQueries({queryKey:["posts"]})
            }
        },
        
    }
) 
}
export default useUpdatePostMutation