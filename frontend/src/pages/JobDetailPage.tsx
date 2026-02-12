import { useFetchJobById } from "@/api/queries/job-listing.queries";
import JobReferBtn from "@/components/functionality/job/job-refer-button";
import JobShareBtn from "@/components/functionality/job/job-share-button";
import { Card, CardHeader } from "@/components/ui/card";
import { useParams } from "react-router-dom"

const JobDetailPage = () => {
    const {jobId} = useParams();
    const {data} = useFetchJobById(Number(jobId))
    return(
        <Card className="w-1/1 p-3">
            <CardHeader className="flex justify-between">
                <div>
                    <h1 className="text-2xl">{data?.data.title}</h1>
                </div>
                <div className="flex gap-2">
                    <JobShareBtn />
                    <JobReferBtn />
                </div>
                
            </CardHeader>

        </Card>
    )
}

export default JobDetailPage