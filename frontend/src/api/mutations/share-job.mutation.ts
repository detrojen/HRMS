import type { TShareJobPayload } from "@/types/apiRequestTypes/TShareJobPayload";
import { shareJob } from "../services/job-service";
import { useMutation } from "@tanstack/react-query";

const useShareJobMutation = () => useMutation(
    {
        mutationFn: (payload:TShareJobPayload) => shareJob(payload)
    }
)

export default useShareJobMutation