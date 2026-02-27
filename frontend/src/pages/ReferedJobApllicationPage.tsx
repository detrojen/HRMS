import {  useFetchReferedJobApplications } from "@/api/queries/job-listing.queries"
import JobApplicationListCard from "@/components/functionality/job/job-appliction-list-card"
import Pageable from "@/components/ui/Pagable"
import type { TJobApplicationResponse } from "@/types/apiResponseTypes/TJobApplicationResponse.type"
import { useSearchParams } from "react-router-dom"

const ReferedJobApplicationListPage = () => {
    const [searchParams] = useSearchParams()

    const {data,isLoading} = useFetchReferedJobApplications(Number(searchParams.get("page")), Number(searchParams.get("limit")))
    return (
        <>
           <div className="">
            {isLoading ? "Loading applications": <Pageable className="grid grid-cols-1 md:grid-cols-3 gap-2" data={data?.data!} render={(item:TJobApplicationResponse)=><JobApplicationListCard key={item.id} {...item}/>} />}
           </div>
        </>
    )
}

export default ReferedJobApplicationListPage