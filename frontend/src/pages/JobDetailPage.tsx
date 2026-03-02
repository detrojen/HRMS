import { useFetchJobById } from "@/api/queries/job-listing.queries";
import JobRefer from "@/components/functionality/job/job-refer";
import JobShare from "@/components/functionality/job/job-share";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { useParams } from "react-router-dom"

const JobDetailPage = () => {
    const { jobId } = useParams();
    const { data:jobQueryData } = useFetchJobById(Number(jobId))
    const jobDetail = jobQueryData?.data.data
    return (
        <Card className="w-1/1 p-3">
            <CardHeader className="flex justify-between">
                <div className="flex">
                    <h1 className="text-2xl">{jobDetail?.title}</h1>
                    <Badge className="h-fit ms-5">{jobDetail?.status}</Badge>
                </div>
                <div className="flex gap-2">
                    <JobShare jobId={Number(jobId)} />
                    <JobRefer jobId={Number(jobId)} />
                </div>
            </CardHeader>
            <CardContent>
                <div className="flex flex-col gap-1">
                    <h3>Skills:-</h3>
                    <div className="flex gap-1 ps-5">
                        {jobDetail?.skills.split(",").map(skill=>
                            <Badge>{skill}</Badge>
                        )}
                    </div>
                </div>
                
                <div className="flex flex-col gap-1 my-2">
                    <h3>Description :-</h3>
                    <p className="ps-5">
                        {jobDetail?.description}
                    </p>
                </div>
                <div className="flex flex-col gap-1">
                    <h3>Other details:-</h3>
                    <div className="ps-5">
                        <h3>Hr:- {jobDetail?.hrOwner.firstName + " " + jobDetail?.hrOwner.lastName}</h3>
                        <p>vacancy:- {jobDetail?.vacancy}</p>
                        <p>work mode:- {jobDetail?.workMode}</p>
                    </div>
                </div>

                
            </CardContent>

        </Card>
    )
}

export default JobDetailPage