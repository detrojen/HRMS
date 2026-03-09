import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { useContext } from "react"
import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import Notification from "./notification"


const Header = () => {
    const { user, logout, switchRole } = useContext(AuthContext)
    
    return (
        <div className="z-50 sticky flex gap-4 justify-end px-3 top-0 left-0 bg-sidebar py-4 border-b-1">
            <Select defaultValue={user.roles.find(role=>role.roleTitle === user.role)?.id.toString()} onValueChange={(value)=>{
                switchRole(Number(value))
            }}>
                <SelectTrigger className="z-100">
                    <SelectValue placeholder="Select a category" />
                </SelectTrigger>
                <SelectContent>
                    <SelectGroup>
                        {
                            user.roles.map(role => (<SelectItem key={role.id} value={role.id.toString()}>{role.roleTitle}</SelectItem>))
                        }
                    </SelectGroup>
                </SelectContent>
            </Select>
            <div>
                <Notification />
                
            </div>
            <div className="flex data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground">
                <Avatar className="h-8 w-8 rounded-3xl me-1">
                    <AvatarImage src={""} alt={user.firstName} />
                    <AvatarFallback className="rounded-lg">{user.firstName[0] + user.lastName[0]}</AvatarFallback>
                </Avatar>
                <div className="grid flex-1 text-left text-sm leading-tight">
                    <span className="truncate font-medium">{user.firstName} {user.lastName}</span>
                    <span className="truncate text-xs">{user.email}</span>
                </div>
            </div>
            <Button onClick={logout}>Logout</Button>
        </div>
    )
}

export default Header