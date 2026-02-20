import { useFetchTravelById } from "@/api/queries/travel.queries"
import ExpenseTab from "@/components/functionality/travel/expense-tab"
import HrExpenseListView from "@/components/functionality/travel/hr-expense-list-view"
import TravelBasicDetail from "@/components/functionality/travel/travel-basic-detail"
import TravelDocumnetDetails from "@/components/functionality/travel/travel-document-detail"
import TravelEmployeeDocumnetDetails from "@/components/functionality/travel/travel-employee-document-detail"
import TravelPersonalDocumnetDetails from "@/components/functionality/travel/travel-personal-documnet-detail"
import { Card } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"

import { useContext } from "react"
import { useParams, useSearchParams } from "react-router-dom"

const TravelDetailPage = () => {
    const { user } = useContext(AuthContext)
    const { travelId } = useParams()
    const [searchParams,setSearchParams] = useSearchParams()
    const { data, isLoading } = useFetchTravelById(travelId!)
    const travelDetail = data?.data
    const handleTabUpdate = (tabName:string) =>{
        searchParams.set("tab" ,tabName)
        setSearchParams(searchParams)
    }
    return <>
        {isLoading?"Loading":<TravelDetailContext.Provider value={travelDetail}>
            <Tabs defaultValue={`${searchParams.get("tab")??"basic-details"}`} className="w-1/1 p-3">
                <TabsList >
                    <TabsTrigger value="basic-details" onClick={()=>handleTabUpdate("basic-details")}>Basic Details</TabsTrigger>
                    <TabsTrigger value="travel-documnets" onClick={()=>handleTabUpdate("travel-documnets")}>Travel Documnet</TabsTrigger>
                    {travelDetail?.inEmployeeList ? <TabsTrigger value="personal-documnets" onClick={()=>handleTabUpdate("personal-documnets")}>Personal Documnet</TabsTrigger> : <></>}
                    {user.role != "Employee" ? <TabsTrigger value="employee-documnets" onClick={()=>handleTabUpdate("employee-documnets")}>Employee Document</TabsTrigger> : <></>}
                    {travelDetail?.inEmployeeList ? <TabsTrigger value="employee-expense" onClick={()=>handleTabUpdate("employee-expense")}>Expense</TabsTrigger> : <></>}
                    {user.role === "HR" ? <TabsTrigger value="hr-expense" onClick={()=>handleTabUpdate("hr-expense")}>Expense</TabsTrigger> : <></>}
                </TabsList>
                <TabsContent value="basic-details">
                    <TravelBasicDetail />
                </TabsContent>
                <TabsContent value="travel-documnets">
                    <TravelDocumnetDetails />
                </TabsContent>
                <TabsContent value="personal-documnets">
                    {travelDetail?.inEmployeeList ? <TravelPersonalDocumnetDetails />: <Card  className="p-3">OOps..! you have not assigned this travel</Card>}
                </TabsContent>
                <TabsContent value="employee-documnets">
                    {user.role != "Employee" ?<TravelEmployeeDocumnetDetails />: <Card className="p-3">You have not access to this data</Card>}
                </TabsContent>
                <TabsContent value="employee-expense">
                    {travelDetail?.inEmployeeList ? <ExpenseTab />: <Card  className="p-3">OOps..! you have not assigned this travel</Card>}
                </TabsContent>
                <TabsContent value="hr-expense">
                    <HrExpenseListView />
                </TabsContent>
            </Tabs>
        </TravelDetailContext.Provider>}

    </>
}

export default TravelDetailPage