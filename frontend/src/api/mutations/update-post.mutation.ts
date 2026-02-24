import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import {  updatePost } from "../services/post.service";
import type { TCreatePostRequest } from "@/types/apiRequestTypes/TCreatePostRequest.type";

const useUpdatePostMutation = () => {
    const navTo = useNavigate();
    return useMutation(
    {
        mutationFn: (payload: TCreatePostRequest) => updatePost(payload),
        onSuccess:(data)=>{
            if(data.status == "OK"){
                navTo("/posts")
            }
        },
        
    }
) 
}
export default useUpdatePostMutation