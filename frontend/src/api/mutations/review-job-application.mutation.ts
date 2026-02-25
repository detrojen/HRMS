import { useMutation, useQueryClient } from "@tanstack/react-query"
import { reviewJobApplication } from "../services/job-service"
import type { TReivewJobApplicationRequest } from "@/types/apiRequestTypes/TReivewJobApplicationRequest.type"
import {useNavigate } from "react-router-dom"

const useReviewJobApplication = () => {
    const navTo = useNavigate()
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload:{review:TReivewJobApplicationRequest,jobApplicationId:number}) => reviewJobApplication(payload)
        ,onSuccess: (data) => {
            navTo("/")
            if(data.status === "OK"){
                queryClient.invalidateQueries({queryKey:["job-applications"]})
            }
        }
    }
)
}

export default useReviewJobApplication