import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type";
import { updateEmployeeTraveldocumnet } from "../services/travel.service";

const useUpdateEmployeeTravelDocumentMutation = () => {
    const queryClient = useQueryClient()

    return useMutation(
        {
            mutationFn: (payload: TUploadTravelDocumnetRequest) => updateEmployeeTraveldocumnet(payload),
            onSuccess: (data) => {
                if (data.status == "OK") {
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            },

        }
    )
}
export default useUpdateEmployeeTravelDocumentMutation