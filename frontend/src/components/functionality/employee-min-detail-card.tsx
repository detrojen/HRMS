import { Link } from "react-router-dom"
import { Avatar, AvatarFallback } from "../ui/avatar"
import { Skeleton } from "../ui/skeleton"

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

export const EmployeeMinDetailSkeletonCard = () => {
    return <>
        <div className="relative flex gap-1 content-center w-1/1">
            <Skeleton className=" h-8 w-8 rounded-4xl my-auto">
                {/* <Skeleton className="rounded-lg"></Skeleton> */}
            </Skeleton>
            <div>
                <Skeleton className="text-xs w-1/1 min-w-50 max-h-10 my-1 text-transparent"><span>.</span></Skeleton>
                <Skeleton className="text-xs w-1/1 min-w-40 max-h-10 my-1 text-transparent" >.</Skeleton>
            </div>
        </div>
    </>
}
export default EmployeeMinDetailCard
