import { useFetchTravels } from "@/api/queries/travel.queries"
import TravelFilter from "@/components/functionality/travel/travel-filter";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Link, useSearchParams } from "react-router-dom";

const TravelList = ({getAsa}:{getAsa:string}) => {
    const[searchParams,setSearchParams] = useSearchParams()
    const { data:travelQueryData,isLoading } = useFetchTravels(getAsa,{
        employees: searchParams.getAll("employees")??undefined,
        title: searchParams.get("title")??undefined,
        startDate: searchParams.get("startDate")??undefined,
        endDate: searchParams.get("endDate")??undefined,
        status: searchParams.get("status")??undefined
    });
    const travels = travelQueryData?.data.data
    if(isLoading){
        return "loading data"
    }
    return (
        <>
            <TravelFilter />
            <Table className="">
                <TableHeader className="">
                    <TableRow>
                        <TableHead >Title</TableHead>
                        <TableHead className="text-center">Start date</TableHead>
                        <TableHead className="text-center">End date</TableHead>
                        <TableHead className="text-center">InititatedBy</TableHead>
                        <TableHead className="text-center">Status</TableHead>
                        <TableHead className="text-center">action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {travels?.map(travel => (
                        <TableRow>
                            <TableCell >{travel.title}</TableCell>
                            <TableCell className="text-center">{travel.startDate}</TableCell>
                            <TableCell className="text-center">{travel.endDate}</TableCell>
                            <TableCell className="text-center">{travel.initiatedBy.firstName} {travel.initiatedBy.lastName}</TableCell>
                            <TableCell className="text-center"><Badge className={"travel-status-"+travel.status.toLowerCase()}>{travel.status}</Badge></TableCell>
                            <TableCell className="text-center">
                                <Link to={`/travels/${travel.id}`} ><Button>Details</Button></Link>
                            </TableCell>
                        </TableRow>))}
                </TableBody>
            </Table>
        </>
    )
}

export default TravelList