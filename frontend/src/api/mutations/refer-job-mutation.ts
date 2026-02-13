import { useMutation } from "@tanstack/react-query";
import { referJob } from "../services/job-service";
import type { TReferJobRequest } from "@/types/apiRequestTypes/TReferJobRequest.type";

const useReferJobMutation = () => useMutation(
    {
        mutationFn: (payload:TReferJobRequest & {jobId: number}) => referJob(payload)
    }
)

export default useReferJobMutation;