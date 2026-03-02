import { useFetchJobApplications } from "@/api/queries/job-listing.queries"
import JobApplicationListCard from "@/components/functionality/job/job-appliction-list-card"
import Pageable from "@/components/ui/Pagable"
import type { TJobApplicationResponse } from "@/types/apiResponseTypes/TJobApplicationResponse.type"
import type { TLayoutContext } from "@/types/TlayoutContext.type"
import { useEffect } from "react"
import { useOutletContext, useSearchParams } from "react-router-dom"

const JobApplicationListPage = () => {
    const [searchParams] = useSearchParams()
    const {setIsLoading} = useOutletContext<TLayoutContext>();
    const {data,isLoading} = useFetchJobApplications(Number(searchParams.get("page")), Number(searchParams.get("limit")))
    const jobApplications = data?.data.data
    useEffect(()=>{
        setIsLoading(isLoading)
    },[isLoading])

    return (
        <>
           <div className="w-1/1 p-2 gap-2">
            {isLoading ? "Loading applications": <Pageable className="grid grid-cols-1 md:grid-cols-3 gap-2 w-1/1" data={jobApplications!} render={(item:TJobApplicationResponse)=><JobApplicationListCard key={item.id} {...item}/>} />}
           </div>
        </>
    )
}

export default JobApplicationListPage