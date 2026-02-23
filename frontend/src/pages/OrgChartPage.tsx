import { useFetchOrgChart, useGetchEmployeesByNameLike } from "@/api/queries/employee.queries"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader } from "@/components/ui/card"
import { Field, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import { Separator } from "@/components/ui/separator"
import { AuthContext } from "@/contexts/AuthContextProvider"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"
import { ArrowDown } from "lucide-react"
import { useContext, useEffect, useState } from "react"
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
    const {user} = useContext(AuthContext)
    const empId = searchParams.get("employeeId")??user.id
    if (emp.manager == null) {
        return <Card onClick={() => { setSearchParams({ "employeeId": emp.id.toString() }) }}>
            <CardHeader>
                <div className="flex gap-1 content-center">
                    <Avatar className="h-8 w-8 rounded-4xl">
                        <AvatarFallback className="rounded-lg">{emp.firstName.slice(0, 2)}</AvatarFallback>
                    </Avatar>
                    <p>{emp.firstName} {emp.lastName}</p>
                </div>
            </CardHeader>
            <CardContent>
                <CardDescription>
                    {emp.designation}
                </CardDescription>
            </CardContent>
        </Card>
    }
    return (
        <>
            <RecursiveComp emp={emp.manager} />
            <ArrowDown className="mx-auto" />
            <Card className={`${empId == emp.id ? "bg-green-300" : ""}`}  onClick={() => { setSearchParams({ "employeeId": emp.id.toString() }) }}>
                <CardHeader>
                <div className="flex gap-1 content-center">
                    <Avatar className="h-8 w-8 rounded-4xl">
                        <AvatarFallback className="rounded-lg">{emp.firstName.slice(0, 2)}</AvatarFallback>
                    </Avatar>
                    <p>{emp.firstName} {emp.lastName}</p>
                </div>
            </CardHeader>
            <CardContent>
                <CardDescription>
                    {emp.designation}
                </CardDescription>
            </CardContent>
            </Card>
        </>
    )
}

const OrgChartPage = () => {
    const [searchParams, setSearchParams] = useSearchParams()
    const orgChartQuery = useFetchOrgChart(searchParams.get("employeeId"))
    const [nameQuery, setNameQuery] = useState("")
    const {data} = useGetchEmployeesByNameLike(nameQuery);
    return <>
        <div className="flex flex-col w-1/1">
            <Field>
          <FieldLabel >Add Reviewers</FieldLabel>
          <Input
            id="search-player"
            type="text"
            placeholder="search player"
            onChange={(e) => { setNameQuery(e.target.value) }}
          />
        </Field>
        {
          data?.map(e => <h1 onClick={() => { setNameQuery(""); setSearchParams({ "employeeId": e.id.toString() })}}>{e.firstName}</h1>)
        }
         {orgChartQuery.data || !orgChartQuery.isLoading ?
            <div className="flex flex-col w-1/2 mx-auto gap-2 p-2 justify-center content-center">
                <RecursiveComp emp={orgChartQuery.data?.data} />
                <Separator />
                <div className="grid grid-cols-6 gap-2">
                    <OneLevelDown  emps={orgChartQuery.data?.data.oneLevelDown} />
                </div>
            </div> : <>Loading org chart</>
        }
        </div>
        
       
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
                    <Avatar className="h-8 w-8 rounded-4xl">
                        <AvatarFallback className="rounded-lg">{emp.firstName.slice(0, 2)}</AvatarFallback>
                    </Avatar>
                    <p>{emp.firstName} {emp.lastName}</p>
                </div>
            </CardHeader>
            <CardContent>
                <CardDescription>
                    {emp.designation}
                </CardDescription>
            </CardContent>
                </Card>
            })}
        </>
    )
}



export default OrgChartPage