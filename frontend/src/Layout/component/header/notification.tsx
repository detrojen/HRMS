import useReadNotificationMutation from "@/api/mutations/read-notification.mutation"
import { useNotifications } from "@/api/queries/notification.queries"
import { Card, CardContent } from "@/components/ui/card"
import { Bell } from "lucide-react"
import NotificationItem from "./notification-item"
import { Button } from "@/components/ui/button"
import { useState } from "react"
import { Separator } from "@/components/ui/separator"
import { Badge } from "@/components/ui/badge"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { ScrollArea } from "@/components/ui/scroll-area"

const Notification = () => {
    // const [open, setOpen] = useState<boolean>(false)
    const [showNotification, setShowNotification] = useState<boolean>(false)
    const readNotifiactionMutation = useReadNotificationMutation()
    const { data } = useNotifications()
    const notifications = data

    return (
        <>
            <Popover open={showNotification} onOpenChange={() => setShowNotification(!showNotification)}>
                <PopoverTrigger asChild>
                    <div className="relative">
                        <Button variant={"outline"}><Bell /></Button>
                        <Badge className="absolute -top-1/4 -right-1/4 bg-green-400">{notifications?.length}</Badge>
                    </div>
                </PopoverTrigger>
                <PopoverContent className="w-100 absolute top-2 -right-60 h-100" align="start">
                    <ScrollArea className="z-100 h-1/1 overflow-y-scroll">
                                {
                                    notifications?.map(
                                        (notifiction) =>
                                            <div onDoubleClick={() => {
                                                readNotifiactionMutation.mutate(notifiction.id)
                                            }}>
                                                <NotificationItem key={`notification-${notifiction.id}`} {...notifiction} />
                                                < Separator />
                                            </div>

                                    )
                                }
                    </ScrollArea>
                </PopoverContent>
            </Popover>


        </>
    )

}
export default Notification