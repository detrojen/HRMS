import { useFetchOrgChart } from "@/api/queries/employee.queries"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader } from "@/components/ui/card"
import { Separator } from "@/components/ui/separator"
import { ArrowDown } from "lucide-react"
import { useEffect } from "react"
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
    const [_, setSearchParams] = useSearchParams()

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
            <Card onClick={() => { setSearchParams({ "employeeId": emp.id.toString() }) }}>
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

const HomePage = () => {
    const [searchParams] = useSearchParams()
    const orgChartQuery = useFetchOrgChart(searchParams.get("employeeId"))
    return <>
        {orgChartQuery.data || !orgChartQuery.isLoading ?
            <div className="flex flex-col w-1/2 mx-auto gap-2 p-2 justify-center content-center">
                <RecursiveComp emp={orgChartQuery.data?.data} />
                <Separator />
                <div className="grid grid-cols-6 gap-2">
                    <OneLevelDown  emps={orgChartQuery.data?.data.oneLevelDown} />
                </div>
            </div> : <>Loading org chart</>
        }
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



export default HomePage