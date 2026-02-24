import { AuthContext } from "@/contexts/AuthContextProvider"
import {  useContext, type PropsWithChildren } from "react"
import { Card, CardTitle } from "../ui/card"

type TRoleCheckProps = {
    roles : string[]
}
const RoleCheck = ({roles, children}:TRoleCheckProps & PropsWithChildren) => {
    const {user} = useContext(AuthContext)
    if(roles.includes(user.role)){
        return children
    }
    return(
        <Card className="w-1/1 text-center">
            <CardTitle>
                Sorry you can not access this page.
            </CardTitle>
        </Card>
    )
}

export default RoleCheck