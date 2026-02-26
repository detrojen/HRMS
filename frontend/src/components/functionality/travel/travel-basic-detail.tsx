import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import { useContext } from "react"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog"
import { Edit } from "lucide-react"
import TravelForm from "@/pages/TravelForm"
import { Link } from "react-router-dom"
import AddEmployeeToTravelAction from "./add-employee-to-travel-action"
import { AuthContext } from "@/contexts/AuthContextProvider"

const TravelBasicDetail = () => {
    const { descripton, startDate, endDate, lastDateToSubmitExpense, title, employees, id, maxReimbursementAmountPerDay } = useContext(TravelDetailContext)
    const {user} = useContext(AuthContext)
    return (
        <Card>
            <CardHeader className="flex gap-3 justify-between">
                <h1>{title}</h1>
                <Link to={"/travels/update/" + id}><Edit /></Link>
            </CardHeader>
            <CardContent>
                <div className="flex flex-col gap-1 ">
                    <p>Start date:- {startDate}</p>
                    <p>End date:- {endDate}</p>
                    <p>Expense accepted till :- {lastDateToSubmitExpense}</p>
                </div>
                <div className="my-2">
                    <h2>Description</h2>
                    <div className="px-2">
                        {descripton}
                    </div>
                </div>
                <div className="flex flex-col gap-3 my-5">
                    <div className="flex w-1/1 gap-2">
                        <h4>Assigned Employees</h4>
                        {user.role === "HR" && <AddEmployeeToTravelAction travelId={id}/>}
                    </div>
                    <div className="grid gird-cols-1 md:grid-cols-4 gap-5">
                        {employees.map(employee => <EmployeeMinDetailCard id={employee.id} key={employee.id} firstName={employee.firstName} lastName={employee.lastName} designation={""} />)}
                    </div>
                </div>
            </CardContent>
        </Card>
    )
}
export default TravelBasicDetail