import { useFetchJobs } from "@/api/queries/job-listing.queries"
import JobInfoCard from "@/components/functionality/job/job-info-card"
import { Card } from "@/components/ui/card"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import Pageable from "@/components/ui/Pagable"
import { useSearchParams } from "react-router-dom"

const JobListPage = () => {
    const [searchParams] = useSearchParams()
    const { data } = useFetchJobs(Number(searchParams.get("page")), Number(searchParams.get("limit")))

    return (
        <Card className="w-1/1 flex flex-col gap-2 px-2" >
            {data && <Pageable data={data.data} render={(job)=><JobInfoCard job={job}/>}/>}
        </Card>
    )
}
export default JobListPage