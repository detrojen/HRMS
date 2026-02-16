import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type";
import { uploadTraveldocumnet } from "../services/travel.service";

const useUploadTravelDocumentMutation = () => {
    const queryClient = useQueryClient()

    return useMutation(
        {
            mutationFn: (payload: TUploadTravelDocumnetRequest) => uploadTraveldocumnet(payload),
            onSuccess: (data, variable, context) => {
                if (data.status == "OK") {
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            },

        }
    )
}
export default useUploadTravelDocumentMutation