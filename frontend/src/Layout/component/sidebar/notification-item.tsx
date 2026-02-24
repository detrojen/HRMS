import { readNotification } from "@/api/services/notification.service"
import type { TNotification } from "@/types/apiResponseTypes/TNotification.type"

const NotificationItem = (props:TNotification) => {

    return (
        <div onClick={
            ()=>{
                readNotification(props.id)
            }
        }>
            <p>{props.message}</p>
        </div>
    )
}

export default NotificationItem