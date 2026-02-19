import { Avatar, AvatarFallback } from "../ui/avatar"

type TEmployeeMinDetailCardProps = {
    firstName: string
    lastName: string
    designation: string
}
const EmployeeMinDetailCard = ({ firstName, lastName, designation }: TEmployeeMinDetailCardProps) => {
    return (
        <div className="flex gap-1 content-center ">
            <Avatar className="h-8 w-8 rounded-4xl my-auto">
                <AvatarFallback className="rounded-lg">{firstName[0] + lastName[0]}</AvatarFallback>
            </Avatar>
            <div>
                <p className="text-xs">{firstName} {lastName}</p>
                <p className="text-xs">{designation}</p>
            </div>
        </div>
    )
}
export default EmployeeMinDetailCard
