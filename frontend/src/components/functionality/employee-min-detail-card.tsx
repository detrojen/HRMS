import { Link } from "react-router-dom"
import { Avatar, AvatarFallback } from "../ui/avatar"

type TEmployeeMinDetailCardProps = {
    firstName: string
    lastName: string
    designation: string
    id: number
}
const EmployeeMinDetailCard = ({ id, firstName, lastName, designation }: TEmployeeMinDetailCardProps) => {
    return (
        <Link to={"/org-chart?employeeId=" + id} className="flex gap-1 content-center ">

            <Avatar className="-z-0 h-8 w-8 rounded-4xl my-auto">
                <AvatarFallback className="rounded-lg">{firstName[0] + lastName[0]}</AvatarFallback>
            </Avatar>
            <div>
                <p className="text-xs">{firstName} {lastName}</p>
                <p className="text-xs">{designation}</p>
            </div>

        </Link>
    )
}
export default EmployeeMinDetailCard
