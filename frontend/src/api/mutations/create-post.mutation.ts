import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { createPost } from "../services/post.service";
import type { TCreatePostRequest } from "@/types/apiRequestTypes/TCreatePostRequest.type";

const useCreatePostMutation = () => {
    const navTo = useNavigate();
    return useMutation(
    {
        mutationFn: (payload: TCreatePostRequest) => createPost(payload),
        onSuccess:(data)=>{
            if(data.status == "OK"){
                navTo("/")
            }
        },
        
    }
) 
}
export default useCreatePostMutation