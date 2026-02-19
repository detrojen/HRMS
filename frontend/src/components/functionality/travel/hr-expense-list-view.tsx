import { Card, CardContent, CardHeader } from "@/components/ui/card"
import DocViewer from "@/components/ui/doc-viewer"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import ExpenseFilter from "./expense-filter"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import { useSearchParams } from "react-router-dom"
import { useContext } from "react"
import { useFetchExpenseAsHR } from "@/api/queries/travel.queries"
import ReviewExpenseAction from "./review-expense-action"


const HrExpenseListView = () => {
    const [searchParams] = useSearchParams()
    const { id } = useContext(TravelDetailContext)
    const params = {
        travelId: id,
        dateFrom: searchParams.get("dateFrom"),
        dateTo: searchParams.get("dateTo"),
        category: searchParams.get("category"),
        employeeId: searchParams.get("employeeId"),
    }
    const { data, isLoading, isError } = useFetchExpenseAsHR(params)
    const expenses = data?.data

    if (isError) return "Error occured"
    return (
        <Card>

            <CardHeader>
                <ExpenseFilter />
            </CardHeader>
            <CardContent>
               <Table className="table-bordered">
                    <TableHeader>
                        <TableRow>
                            <TableHead className="text-center">Date</TableHead>
                            <TableHead className="text-center">Category</TableHead>
                            <TableHead className="text-center">Asked amt</TableHead>
                            <TableHead className="text-center">Expensed by</TableHead>
                            <TableHead className="text-center">Aprooved amt</TableHead>
                            <TableHead className="text-center">Aprooved by</TableHead>
                            <TableHead className="text-center">Remark</TableHead>
                            <TableHead className="text-center">Actions</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        { isLoading ? <TableRow><TableCell className="text-center" colSpan={8}>Loading data</TableCell></TableRow> :
                            expenses?.map(expense => (
                                <TableRow key={`expense-${expense.id}`}>
                                    <TableCell className="text-center">{expense.dateOfExpense.toString()}</TableCell>
                                    <TableCell className="text-center">{expense.category.category}</TableCell>
                                    <TableCell className="text-center">{expense.askedAmount}</TableCell>
                                    <TableCell className="text-center">{expense.employee.firstName + " " + expense.employee.lastName}</TableCell>
                                    <TableCell className="text-center">{expense.aprrovedAmount}</TableCell>
                                    <TableCell className="text-center">{expense.reviewedBy ?
                                        expense.reviewedBy.firstName + " " + expense.reviewedBy.lastName :
                                        "-"}
                                    </TableCell>
                                    <TableCell className="text-center">{expense.remark}</TableCell>
                                    <TableCell className="text-center">
                                        <DocViewer url={`/api/resource/expenses/${expense.reciept}`} />
                                        <ReviewExpenseAction {...expense} />

                                    </TableCell>
                                </TableRow>
                            ))
                        }
                    </TableBody>
                </Table>

            </CardContent>

        </Card>
    )
}

export default HrExpenseListView