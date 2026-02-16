import useAddExpenseMutation from "@/api/mutations/add-expense.mutation"
import useUpdateExpenseMutation from "@/api/mutations/update-expense.mutation"
import useUploadEmployeeTravelDocumentMutation from "@/api/mutations/upload-employee-travel-document.mutation"
import useUploadTravelDocumentMutation from "@/api/mutations/upload-travel-document.mutation"
import { useFetchTravelById } from "@/api/queries/travel.queries"
import AddTravelDocumnetAction from "@/components/functionality/travel/add-travel-documnet-action"
import AddUpdateExpenseAction from "@/components/functionality/travel/add-update-expense-action"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { AuthContext } from "@/contexts/AuthContextProvider"
import type { TTravelDetails } from "@/types/apiResponseTypes/TTravelDetails.type"
import type { TTravelDoucmentResponse } from "@/types/apiResponseTypes/TTravelDocumnetResponse.type"
import type { TTravelExpenseResponse } from "@/types/apiResponseTypes/TTravelExpenseResponse.type"
import { useContext, useEffect } from "react"
import { useParams } from "react-router-dom"

const TravelBasicDetail = ({ data }: { data: Pick<TTravelDetails, "descripton" | "initiatedBy" | "startDate" | "endDate" | "lastDateToSubmitExpense" | "maxReimbursementAmountPerDay" | "title"> }) => {

    return (
        <Card>
            <CardHeader>
                <h1>{data.title}</h1>
            </CardHeader>
            <CardContent>
                <div className="flex flex-col gap-1 ">
                    <p>Start date:- {data.startDate}</p>
                    <p>End date:- {data.endDate}</p>
                    <p>Expense accepted till :- {data.lastDateToSubmitExpense}</p>
                </div>
                <div className="my-2">
                    <h2>Description</h2>
                    <div className="px-2">
                        {data.descripton}
                    </div>
                </div>
            </CardContent>
        </Card>
    )
}

const TravelDocumentTable = ({ documents, canUpdate }: { documents: TTravelDoucmentResponse[], canUpdate: boolean }) => {
    return (
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead>Type</TableHead>
                    <TableHead>Description</TableHead>
                    <TableHead>Uploaded by</TableHead>
                    <TableHead>Action</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    documents.map(document => (
                        <TableRow>
                            <TableCell>{document.type}</TableCell>
                            <TableCell>{document.description}</TableCell>
                            <TableCell>{document.uploadedBy.firstName} {document.uploadedBy.lastName}</TableCell>
                            <TableCell>
                                <Button>View</Button>
                                {canUpdate ? <Button>Update</Button> : <></>}
                            </TableCell>
                        </TableRow>
                    ))
                }

            </TableBody>
        </Table>
    )
}

const TravelDocumnetDetails = ({ documents, travelId }: { documents: TTravelDoucmentResponse[], travelId: number }) => {
    const { user } = useContext(AuthContext);
    return (
        <Card className="w-1/1">
            <CardContent>
                {user.role == "HR" ? <div className="w-1/1 flex justify-end ">
                    <AddTravelDocumnetAction travelId={travelId} mutation={useUploadTravelDocumentMutation} />
                </div> : <></>}
                <TravelDocumentTable documents={documents} canUpdate={user.role == "HR"} />
            </CardContent>
        </Card>
    )
}

const TravelPersonalDocumnetDetails = ({ documents, travelId }: { documents: TTravelDoucmentResponse[], travelId: number }) => {
    return (
        <Card className="w-1/1">
            <CardContent>
                <div className="w-1/1 flex justify-end ">
                    <AddTravelDocumnetAction travelId={travelId} mutation={useUploadEmployeeTravelDocumentMutation} />
                </div>
                <TravelDocumentTable documents={documents} canUpdate={true} />
            </CardContent>
        </Card>
    )
}

const TravelEmployeeDocumnetDetails = ({ documents }: { documents: TTravelDoucmentResponse[] }) => {
    return (
        <Card className="w-1/1">
            <CardContent>
                <TravelDocumentTable documents={documents} canUpdate={false} />
            </CardContent>
        </Card>
    )
}

const ExpenseTab = ({ travelId, expenses }: { travelId: number | string, expenses: TTravelExpenseResponse[] }) => {
    return (
        <>
            <Card>
                <AddUpdateExpenseAction travelId={travelId} mutation={useAddExpenseMutation} />
                <Table className="table-bordered">
                    <TableHeader>
                        <TableRow>
                            <TableHead className="text-center">Date</TableHead>
                            <TableHead className="text-center">Category</TableHead>
                            <TableHead className="text-center">Asked amt</TableHead>
                            <TableHead className="text-center">Aprooved amt</TableHead>
                            <TableHead className="text-center">Aprooved by</TableHead>
                            <TableHead className="text-center">Remark</TableHead>
                            <TableHead className="text-center">Actions</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {
                            expenses.map(expense => (
                                <TableRow key={`expense-${expense.id}`}>
                                    <TableCell className="text-center">{expense.dateOfExpense.toString()}</TableCell>
                                    <TableCell className="text-center">{expense.category.category}</TableCell>
                                    <TableCell className="text-center">{expense.askedAmout}</TableCell>
                                    <TableCell className="text-center">{expense.aprrovedAmout}</TableCell>
                                    <TableCell className="text-center">{expense.reviedBy ?
                                        expense.reviedBy.firstName + " " + expense.reviedBy.lastName :
                                        "-"}
                                    </TableCell>
                                    <TableCell className="text-center">{expense.remark}</TableCell>
                                    <TableCell>
                                        <AddUpdateExpenseAction travelId={travelId}
                                            expense={{
                                                expenseDetails: {
                                                    categoryId: expense.category.id.toString(),
                                                    dateOfExpense: expense.dateOfExpense, 
                                                    askedAmout: expense.askedAmout, 
                                                    id: expense.id, 
                                                    reciept: expense.reciept
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

const TravelDetailPage = () => {
    const { user } = useContext(AuthContext)
    const { travelId } = useParams()
    const { data } = useFetchTravelById(travelId!)
    const travelDetail = data?.data
    return <>
        <Tabs defaultValue="overview" className="w-1/1 p-3">
            <TabsList defaultValue={"basic-details"}>
                <TabsTrigger value="basic-details">Basic Details</TabsTrigger>
                <TabsTrigger value="travel-documnets">Travel Documnet</TabsTrigger>
                {travelDetail?.inEmployeeList ? <TabsTrigger value="personal-documnets">Personal Documnet</TabsTrigger> : <></>}
                {user.role != "Employee" ? <TabsTrigger value="employee-documnets">Employee Document</TabsTrigger> : <></>}
                {travelDetail?.inEmployeeList ? <TabsTrigger value="expense">Expense</TabsTrigger> : <></>}
            </TabsList>
            <TabsContent value="basic-details">
                <TravelBasicDetail data={{ ...travelDetail }} />
            </TabsContent>
            <TabsContent value="travel-documnets">
                <TravelDocumnetDetails travelId={travelDetail?.id} documents={travelDetail?.travelDocuments} />
            </TabsContent>
            <TabsContent value="personal-documnets">
                <TravelPersonalDocumnetDetails travelId={travelDetail?.id} documents={travelDetail?.personalDocumnets} />
            </TabsContent>
            <TabsContent value="employee-documnets">
                <TravelEmployeeDocumnetDetails documents={travelDetail?.employeeDocuments} />
            </TabsContent>
            <TabsContent value="expense">
                <ExpenseTab travelId={travelDetail?.id} expenses={travelDetail?.expenses} />
            </TabsContent>
        </Tabs>
    </>
}

export default TravelDetailPage