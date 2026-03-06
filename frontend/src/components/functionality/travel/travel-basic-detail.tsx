import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import { useContext } from "react"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import { Edit } from "lucide-react"
import { Link } from "react-router-dom"
import AddEmployeeToTravelAction from "./add-employee-to-travel-action"
import { AuthContext } from "@/contexts/AuthContextProvider"
import RoleCheck from "../role-check"

const TravelBasicDetail = () => {
    const { descripton, startDate, endDate, lastDateToSubmitExpense, title, employees, id, stats } = useContext(TravelDetailContext)
    const { user } = useContext(AuthContext)
    return (
        <Card>
            <CardHeader className="flex gap-3 justify-between">
                <h1>{title}</h1>
                {user.role === "HR" && <Link to={"/travels/update/" + id}><Edit /></Link>}
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
                        {user.role === "HR" && <AddEmployeeToTravelAction travelId={id} />}
                    </div>
                    <div className="grid gird-cols-1 md:grid-cols-4 gap-5">
                        {employees.map(employee => <EmployeeMinDetailCard id={employee.id} key={employee.id} firstName={employee.firstName} lastName={employee.lastName} designation={""} />)}
                    </div>
                </div>

                {user.role === "HR" && <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
                    <Card>
                        <CardHeader>
                            <CardTitle>Total Employees</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalEmployees}</CardDescription>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Total Documents</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalDocuments}</CardDescription>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Total EmployeeDocuments</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalEmployeeDocuments}</CardDescription>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Total Expenses</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalExpenses}</CardDescription>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Total Expense Amount</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalAskedExpenseAmount}</CardDescription>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Total Approved Amount</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalApprovedExpenseAmount}</CardDescription>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Total Pending Expenses</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalPendingExpensesToReview}</CardDescription>
                        </CardContent>
                    </Card>
                    <Card>
                        <CardHeader>
                            <CardTitle>Total Reviewed Expense</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <CardDescription className="text-xl">{stats.totalReviewedExpense}</CardDescription>
                        </CardContent>
                    </Card>
                </div>}

            </CardContent>
        </Card>
    )
}
export default TravelBasicDetail