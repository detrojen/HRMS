import { useMutation } from "@tanstack/react-query"
import { reviewCv } from "../services/job-service"
import type { TReviewCvRequest } from "@/types/apiRequestTypes/TReviewCvRequest.type"

const useReviewCvMutation = () => {
    return useMutation({
        mutationFn: (payload:{review:TReviewCvRequest,jobApplicationId:number}) => reviewCv(payload)
    })
}

export default useReviewCvMutation