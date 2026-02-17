import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import api from "../api"
import type { TNotification } from "@/types/apiResponseTypes/TNotification.type"

export const fetchNotifications = () => {
    return api.get<TGlobalResponse<TNotification[]>>("/api/notifications")
}

export const readNotification = (notificationTemplateId:number) =>{
    return api.patch(`/api/notifications/read/${notificationTemplateId}`)
}