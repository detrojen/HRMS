import { readNotification } from "@/api/services/notification.service"
import type { TNotification } from "@/types/apiResponseTypes/TNotification.type"

const NotificationItem = (props:TNotification) => {

    return (
        
            <p className="text-sm my-2">{props.message}</p>

    )
}

export default NotificationItem