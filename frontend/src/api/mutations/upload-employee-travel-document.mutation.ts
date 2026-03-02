import { useMutation, useQueryClient } from "@tanstack/react-query";
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type";
import { uploadEmployeeTraveldocumnet } from "../services/travel.service";

const useUploadEmployeeTravelDocumentMutation = () => {
    const queryClient = useQueryClient()

    return useMutation(
        {
            mutationFn: (payload: TUploadTravelDocumnetRequest) => uploadEmployeeTraveldocumnet(payload),
            onSuccess: (data) => {
                if (data.data.status == "OK") {
                    queryClient.invalidateQueries({queryKey:["travel-by-id"]})
                }
            },

        }
    )
}
export default useUploadEmployeeTravelDocumentMutation