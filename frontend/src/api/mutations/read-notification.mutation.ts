import { useMutation, useQueryClient } from "@tanstack/react-query";
import { readNotification } from "../services/notification.service";
import type { TNotification } from "@/types/apiResponseTypes/TNotification.type";
import type { AxiosResponse } from "axios";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";

const useReadNotificationMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (notificationId: number) => readNotification(notificationId),
            onSuccess: (data, notificationId) => {
                if (data.data.status === "OK") {
                    queryClient.setQueryData(["notification"],(data:AxiosResponse<TGlobalResponse<TNotification[]>>)=>({
                        ...data,
                        data: {
                            ...data.data
                            ,data: data.data.data.filter(notification=>notification.id!=notificationId)
                        }
                    }))
                }
            },
            
        }
    )
}

export default useReadNotificationMutation