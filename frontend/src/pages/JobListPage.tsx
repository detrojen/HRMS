import { useFetchJobs } from "@/api/queries/job-listing.queries"
import JobInfoCard from "@/components/functionality/job/job-info-card"
import { Card } from "@/components/ui/card"
import Pageable from "@/components/ui/Pagable"
import JonInfoSkeletonCard from "@/components/ui/skeleton-cards/jon-info-skeleton-card"
import SkeletonList from "@/components/ui/SkeletonList"
import { useSearchParams } from "react-router-dom"

const JobListPage = () => {
    const [searchParams] = useSearchParams()
    const { data,isLoading } = useFetchJobs(Number(searchParams.get("page")), Number(searchParams.get("limit")))
    const jobs = data?.data.data
    if(isLoading){
        <SkeletonList className="flex flex-col gap-2 px-2" items={5} render={()=><JonInfoSkeletonCard />}/>
    }
    return (
        <Card className="w-1/1 " >
            {jobs && <Pageable className="flex flex-col gap-2 px-2" data={jobs} render={(job)=><JobInfoCard key={job.id} job={job}/>}/>}
        </Card>
    )
}
export default JobListPage