import { useFetchJobById } from "@/api/queries/job-listing.queries";
import JobRefer from "@/components/functionality/job/job-refer";
import JobShare from "@/components/functionality/job/job-share";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { useParams } from "react-router-dom"

const JobDetailPage = () => {
    const { jobId } = useParams();
    const { data } = useFetchJobById(Number(jobId))
    return (
        <Card className="w-1/1 p-3">
            <CardHeader className="flex justify-between">
                <div className="flex">
                    <h1 className="text-2xl">{data?.data.title}</h1>
                    <Badge className="h-fit ms-5">{data?.data.status}</Badge>
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
                        {data?.data.skills.split(",").map(skill=>
                            <Badge>{skill}</Badge>
                        )}
                    </div>
                </div>
                
                <div className="flex flex-col gap-1 my-2">
                    <h3>Description :-</h3>
                    <md-block className="ps-5">
                        {data?.data.description}
                    </md-block>
                </div>
                <div className="flex flex-col gap-1">
                    <h3>Other details:-</h3>
                    <div className="ps-5">
                        <h3>Hr:- {data?.data.hrOwner.firstName + " " + data?.data.hrOwner.lastName}</h3>
                        <p>vacancy:- {data?.data.vacancy}</p>
                        <p>work mode:- {data?.data.workMode}</p>
                    </div>
                </div>

                
            </CardContent>

        </Card>
    )
}

export default JobDetailPage