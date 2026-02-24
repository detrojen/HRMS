import { useFetchJobApplications } from "@/api/queries/job-listing.queries"
import JobApplicationListCard from "@/components/functionality/job/job-appliction-list-card"
import Pageable from "@/components/ui/Pagable"
import type { TJobApplicationResponse } from "@/types/apiResponseTypes/TJobApplicationResponse.type"
import type { ReactNode } from "react"
import { useSearchParams } from "react-router-dom"

const JobApplicationListPage = () => {
    const [searchParams] = useSearchParams()

    const {data,isLoading} = useFetchJobApplications(Number(searchParams.get("page")), Number(searchParams.get("limit")))
    return (
        <>
           <div className="grid grid-cols-2 gap-2">
            {isLoading ? "Loading applications": <Pageable data={data?.data!} render={(item:TJobApplicationResponse)=><JobApplicationListCard key={item.id} {...item}/>} />}
           </div>
        </>
    )
}

export default JobApplicationListPage