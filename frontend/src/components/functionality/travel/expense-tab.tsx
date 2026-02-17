import useUpdateExpenseMutation from "@/api/mutations/update-expense.mutation"
import { Card } from "@/components/ui/card"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import AddUpdateExpenseAction from "./add-update-expense-action"
import DocViewer from "@/components/ui/doc-viewer"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import { Home } from "lucide-react"
import useAddExpenseMutation from "@/api/mutations/add-expense.mutation"
import { Badge } from "@/components/ui/badge"

const ExpenseTab = () => {
    const travelDetail = useContext(TravelDetailContext)
    travelDetail.expensesMadeByMe
    const {expensesMadeByMe,  id: travelId } = useContext(TravelDetailContext)
    return (
        <>
            <Card>
                <AddUpdateExpenseAction title="add" travelId={travelId} mutation={useAddExpenseMutation} />
                <Table className="table-bordered">
                    <TableHeader>
                        <TableRow>
                            <TableHead className="text-center">Date</TableHead>
                            <TableHead className="text-center">Category</TableHead>
                            <TableHead className="text-center">Asked amt</TableHead>
                            <TableHead className="text-center">Aprooved amt</TableHead>
                            <TableHead className="text-center">Aprooved by</TableHead>
                            <TableHead className="text-center">Remark</TableHead>
                            <TableHead className="text-center">Status</TableHead>
                            <TableHead className="text-center">Actions</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {
                           
                            expensesMadeByMe?.map(expense => (
                                <TableRow key={`expense-${expense.id}`}>
                                    <TableCell className="text-center">{expense.dateOfExpense.toString()}</TableCell>
                                    <TableCell className="text-center">{expense.category.category}</TableCell>
                                    <TableCell className="text-center">{expense.askedAmount}</TableCell>
                                    <TableCell className="text-center">{expense.aprrovedAmount}</TableCell>
                                    <TableCell className="text-center">{expense.reviewedBy ?
                                        expense.reviewedBy.firstName + " " + expense.reviewedBy.lastName :
                                        "-"}
                                    </TableCell>
                                    <TableCell className="text-center">{expense.remark}</TableCell>
                                    <TableCell className="text-center">
                                        <Badge className="expense-approved">
                                            {expense.status} {`expense-${[expense.status]}`}
                                        </Badge>
                                    </TableCell>
                                    <TableCell>
                                            <DocViewer url={`/api/resource/jds/${expense.reciept}`} /> 

                                        <AddUpdateExpenseAction title="update" icon={Home} travelId={travelId}
                                            expense={{
                                                expenseDetails: {
                                                    categoryId: expense.category.id.toString(),
                                                    ...expense
                                                }
                                            }} mutation={useUpdateExpenseMutation} />
                                    </TableCell>
                                </TableRow>
                            ))
                        }
                    </TableBody>
                </Table>
            </Card>
        </>
    )
}

export default ExpenseTab
