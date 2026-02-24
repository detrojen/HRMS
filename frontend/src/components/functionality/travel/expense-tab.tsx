import useUpdateExpenseMutation from "@/api/mutations/update-expense.mutation"
import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import AddUpdateExpenseAction from "./add-update-expense-action"
import DocViewer from "@/components/ui/doc-viewer"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import { Home } from "lucide-react"
import useAddExpenseMutation from "@/api/mutations/add-expense.mutation"
import { Badge } from "@/components/ui/badge"
import type { TTravelExpenseResponse } from "@/types/apiResponseTypes/TTravelExpenseResponse.type"
type TReducedExpense = {
    askedAmount: number
    approvedAmount: number
    totalApproved:number
    totalRejected:number
    totalPending:number
}

const calcReducedExpense= (acc:TReducedExpense,expense:TTravelExpenseResponse):TReducedExpense => {
    return {
        askedAmount: acc.askedAmount + expense.askedAmount,
        approvedAmount: acc.approvedAmount + expense.aprrovedAmount,
        totalApproved: acc.totalApproved + (expense.status === "approved"?1:0),
        totalRejected: acc.totalRejected + (expense.status === "rejected"?1:0),
        totalPending: acc.totalPending + (expense.status === "pending"?1:0)
    }
}

const ExpenseTab = () => {
    const travelDetail = useContext(TravelDetailContext)
    const { expensesMadeByMe, id: travelId } = useContext(TravelDetailContext)
    const reducedData = expensesMadeByMe.reduce<TReducedExpense>((acc:TReducedExpense,expense)=>{return calcReducedExpense(acc,expense)}, {askedAmount:0,approvedAmount:0,totalApproved:0,totalRejected:0,totalPending:0})
    return (
        <>
            <Card>
                <div className="flex justify-end pe-2">
                <AddUpdateExpenseAction title="add" travelId={travelId} mutation={useAddExpenseMutation} />
                </div>

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
                                        <Badge className={`expense-${[expense.status]}`}>
                                            {expense.status} 
                                        </Badge>
                                    </TableCell>
                                    <TableCell className="flex gap-2">
                                        <DocViewer url={`/api/resource/expenses/${expense.reciept}`} />

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
            <div className="flex gap-1 my-2">
                <Card className="w-50 ">
                    <CardHeader>
                        <h1>Total Asked:</h1>
                    </CardHeader>
                    <CardContent>
                       {reducedData.askedAmount}
                    </CardContent>
                </Card>
                <Card className="w-50 ">
                    <CardHeader>
                        <h1>Total approoved:</h1>
                    </CardHeader>
                    <CardContent>
                        {reducedData.approvedAmount}
                    </CardContent>
                </Card>
                <Card className="w-50 ">
                    <CardHeader>
                        <h1>Total Pending:</h1>
                    </CardHeader>
                    <CardContent>
                        {reducedData.totalPending}
                    </CardContent>
                </Card>
                <Card className="w-50 ">
                    <CardHeader>
                        <h1>Total Approved:</h1>
                    </CardHeader>
                    <CardContent>
                        {reducedData.totalApproved}
                    </CardContent>
                </Card>
                <Card className="w-50 ">
                    <CardHeader>
                        <h1>Total Rejected:</h1>
                    </CardHeader>
                    <CardContent>
                        {reducedData.totalRejected}
                    </CardContent>
                </Card>
            </div>
        </>
    )
}

export default ExpenseTab
