import { useFetchTravels } from "@/api/queries/travel.queries"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Link } from "react-router-dom";

const TravelList = ({getAsa}:{getAsa:string}) => {
    const { data,isLoading } = useFetchTravels(getAsa);
    const travels = data?.data
    if(isLoading){
        return "loading data"
    }
    return (
        <>
            <Table className="">
                <TableHeader className="">
                    <TableRow>
                        <TableHead>Title</TableHead>
                        <TableHead>Start date</TableHead>
                        <TableHead>End date</TableHead>
                        <TableHead>InititatedBy</TableHead>
                        <TableHead>action</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {travels?.map(travel => (
                        <TableRow>
                            <TableCell>{travel.title}</TableCell>
                            <TableCell>{travel.startDate}</TableCell>
                            <TableCell>{travel.endDate}</TableCell>
                            <TableCell>{travel.initiatedBy.firstName} {travel.initiatedBy.lastName}</TableCell>
                            <TableCell>
                                <Link to={"/travels/1"} >get more...</Link>
                            </TableCell>
                        </TableRow>))}
                </TableBody>
            </Table>
        </>
    )
}

export default TravelList