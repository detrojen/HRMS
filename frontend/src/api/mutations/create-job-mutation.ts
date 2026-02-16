import type { TCreateJobRequest } from "@/types/apiRequestTypes/TCreateJobRequest.type";
import { useMutation } from "@tanstack/react-query";
import { createJobRequest } from "../services/job-service";
import { useNavigate } from "react-router-dom";

const useCreateJobMutation = () => {
    const navTo = useNavigate();
    return useMutation(
    {
        mutationFn: (payload: TCreateJobRequest) => createJobRequest(payload),
        onSuccess:(data,variable,context)=>{
            if(data.status == "OK"){
                navTo("/")
            }
        },
        
    }
) 
}
export default useCreateJobMutation