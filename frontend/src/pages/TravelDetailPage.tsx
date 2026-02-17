import { useFetchTravelById } from "@/api/queries/travel.queries"
import ExpenseTab from "@/components/functionality/travel/expense-tab"
import HrExpenseListView from "@/components/functionality/travel/hr-expense-list-view"
import TravelBasicDetail from "@/components/functionality/travel/travel-basic-detail"
import TravelDocumnetDetails from "@/components/functionality/travel/travel-document-detail"
import TravelEmployeeDocumnetDetails from "@/components/functionality/travel/travel-employee-document-detail"
import TravelPersonalDocumnetDetails from "@/components/functionality/travel/travel-personal-documnet-detail"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"

import { useContext } from "react"
import { useParams } from "react-router-dom"

const TravelDetailPage = () => {
    const { user } = useContext(AuthContext)
    const { travelId } = useParams()
    const { data } = useFetchTravelById(travelId!)
    const travelDetail = data?.data
    return <>
        <TravelDetailContext.Provider value={travelDetail}>
            <Tabs defaultValue="overview" className="w-1/1 p-3">
                <TabsList defaultValue={"basic-details"}>
                    <TabsTrigger value="basic-details">Basic Details</TabsTrigger>
                    <TabsTrigger value="travel-documnets">Travel Documnet</TabsTrigger>
                    {travelDetail?.inEmployeeList ? <TabsTrigger value="personal-documnets">Personal Documnet</TabsTrigger> : <></>}
                    {user.role != "Employee" ? <TabsTrigger value="employee-documnets">Employee Document</TabsTrigger> : <></>}
                    {travelDetail?.inEmployeeList ? <TabsTrigger value="employee-expense">Expense</TabsTrigger> : <></>}
                    {user.role === "HR" ? <TabsTrigger value="hr-expense">Expense</TabsTrigger> : <></>}
                </TabsList>
                <TabsContent value="basic-details">
                    <TravelBasicDetail />
                </TabsContent>
                <TabsContent value="travel-documnets">
                    <TravelDocumnetDetails />
                </TabsContent>
                <TabsContent value="personal-documnets">
                    <TravelPersonalDocumnetDetails />
                </TabsContent>
                <TabsContent value="employee-documnets">
                    <TravelEmployeeDocumnetDetails />
                </TabsContent>
                <TabsContent value="employee-expense">
                    <ExpenseTab />
                </TabsContent>
                <TabsContent value="hr-expense">
                    <HrExpenseListView />
                </TabsContent>
            </Tabs>
        </TravelDetailContext.Provider>

    </>
}

export default TravelDetailPage