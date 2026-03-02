import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type";
import { updateTraveldocumnet } from "../services/travel.service";

const useUpdateTravelDocumentMutation = () => {
    const queryClient = useQueryClient()

    return useMutation(
        {
            mutationFn: (payload: TUploadTravelDocumnetRequest) => updateTraveldocumnet(payload),
            onSuccess: (data) => {
                if (data.data.status == "OK") {
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            },

        }
    )
}
export default useUpdateTravelDocumentMutation