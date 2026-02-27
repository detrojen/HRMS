import { useFetchOrgChart, useGetchEmployeesByNameLike } from "@/api/queries/employee.queries"
import EmployeeMinDetailCard from "@/components/functionality/employee-min-detail-card"
import { Button } from "@/components/ui/button"
import { Card, CardHeader } from "@/components/ui/card"
import Searchable from "@/components/ui/searchable"
import { Separator } from "@/components/ui/separator"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { ArrowDown } from "lucide-react"
import { useContext, useState } from "react"
import { useSearchParams } from "react-router-dom"
type TEmp = {
    oneLevelDown?: TEmp[],
    id: number,
    firstName: string,
    lastName: string,
    designation: string,
    manager?: TEmp | null,
    managerId?: number
}
const RecursiveComp = ({ emp }: { emp: TEmp }) => {
    const [searchParams, setSearchParams] = useSearchParams()
    const { user } = useContext(AuthContext)
    const empId = searchParams.get("employeeId") ?? user.id
    if (emp.manager == null) {
        return <Card className={`${empId == emp.id ? "bg-green-300" : ""}`} onClick={() => { setSearchParams({ "employeeId": emp.id.toString() }) }}>
            <CardHeader>
                <div className="flex gap-1 content-center">
                    <EmployeeMinDetailCard id={emp.id} firstName={emp.firstName} lastName={emp.lastName} designation={emp.designation} />

                </div>
            </CardHeader>
        </Card>
    }
    return (
        <>
            <RecursiveComp emp={emp.manager} />
            <ArrowDown className="mx-auto" />
            <Card className={`${empId == emp.id ? "bg-green-300" : ""}`} onClick={() => { setSearchParams({ "employeeId": emp.id.toString() }) }}>
                <CardHeader>
                    <div className="flex gap-1 content-center">
                        <EmployeeMinDetailCard id={emp.id} firstName={emp.firstName} lastName={emp.lastName} designation={emp.designation} />

                    </div>
                </CardHeader>
            </Card>
        </>
    )
}

const OrgChartPage = () => {
    const [searchParams, setSearchParams] = useSearchParams()
    const orgChartQuery = useFetchOrgChart(searchParams.get("employeeId"))
    const [nameQuery, setNameQuery] = useState("")
    const { data } = useGetchEmployeesByNameLike(nameQuery);
    return <>
        <div className="flex flex-col w-1/1">
            <Searchable 
                data={data??[]}
                setQuery={setNameQuery}
                render={(item)=><h1>{item.firstName} {item.lastName}</h1>}
                onSelectItem={(item)=>{
                    setNameQuery("")
                    setSearchParams({ "employeeId": item.id.toString()})
                }}
            >
                <Button>
                    Serach Employee
                </Button>
            </Searchable>
            
           
            {orgChartQuery.data || !orgChartQuery.isLoading ?
                <div className="flex flex-col w-1/2 mx-auto gap-2 p-2 justify-center content-center">
                    <RecursiveComp emp={orgChartQuery.data?.data} />
                    <Separator />
                    <div className="grid grid-cols-6 gap-2">
                        <OneLevelDown emps={orgChartQuery.data?.data.oneLevelDown} />
                    </div>
                </div> : <>Loading org chart</>
            }
        </div >


    </>
}

const OneLevelDown = ({ emps }: { emps: TEmp[] }) => {
    const [_, setSearchParams] = useSearchParams()
    return (
        <>
            {emps.map(emp => {
                return <Card className="col-span-2" onClick={() => { setSearchParams({ "employeeId": emp.id.toString() }) }}>
                    <CardHeader>
                        <div className="flex gap-1 content-center">
                            <EmployeeMinDetailCard id={emp.id} firstName={emp.firstName} lastName={emp.lastName} designation={emp.designation} />
                        </div>
                    </CardHeader>
                </Card>
            })}
        </>
    )
}



export default OrgChartPage