import { useNotifications } from "@/api/queries/notification.queries"
import { AvatarImage, AvatarFallback } from "@/components/ui/avatar"
import { DropdownMenu, DropdownMenuContent, DropdownMenuGroup, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
import { useSidebar } from "@/components/ui/sidebar"
import { BadgeCheck, Bell, CreditCard, LogOut, Sparkles } from "lucide-react"
import type { Avatar } from "radix-ui"
import { useEffect } from "react"
import NotificationItem from "./notification-item"

const Notification = () => {
    const { isMobile } = useSidebar()
    const {data} = useNotifications()
    const notifications = data?.data
    return (
        <>
            <DropdownMenu>
                <DropdownMenuTrigger>
                    <DropdownMenuItem>
                        <Bell />Notification
                    </DropdownMenuItem>
                    
                </DropdownMenuTrigger>
                <DropdownMenuContent
                    className="rounded-lg"
                    side={isMobile ? "bottom" : "right"}
                    align="end"
                    sideOffset={4}
                >
                    <DropdownMenuLabel className="p-0 font-normal">
                        <div className="flex items-center gap-2 px-1 py-1.5 text-left text-sm">
                           <Bell />Notification
                        </div>
                    </DropdownMenuLabel>
                    <DropdownMenuSeparator />
                    
                    <DropdownMenuGroup>
                        <DropdownMenuItem>
                            {
                                notifications?.map(
                                    (notifiction)=><NotificationItem key={`notification-${notifiction.id}`} {...notifiction}/>
                                )
                            }
                        </DropdownMenuItem>
                        
                    </DropdownMenuGroup>
                </DropdownMenuContent>
            </DropdownMenu>
        </>
    )
}

export default Notification