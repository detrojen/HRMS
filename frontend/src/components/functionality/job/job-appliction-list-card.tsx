import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import type { TJobApplicationResponse } from "@/types/apiResponseTypes/TJobApplicationResponse.type"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import DocViewer, { DocRenderer } from "@/components/ui/doc-viewer"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Link } from "react-router-dom"

const JobApplicationListCard = (jobApplication: TJobApplicationResponse) => {
    return (
        <>
            <Card>
                <CardHeader>
                    <CardTitle>Applied for {jobApplication.job.title}</CardTitle>
                    <Badge variant={"outline"} className="bg-green-200">{jobApplication.status}</Badge>
                </CardHeader>
                <CardContent>
                    <p>Applicants details:-</p>
                    <li>
                        <ul>Name: - {jobApplication.applicantsName}</ul>
                        <ul>email: - {jobApplication.applicantsEmail}</ul>
                        <ul>phone: - {jobApplication.applicantsPhone}</ul>
                    </li>
                    <div className="my-5">
                        refered by:- <EmployeeMinDetailCard id={jobApplication.referedBy.id} firstName={jobApplication.referedBy.firstName} lastName={jobApplication.referedBy.lastName} designation={jobApplication.referedBy.designation} />
                    </div>
                    
                    {
                        jobApplication.reviewedBy
                        && <div className="flex flex-wrap gap-1 my-5">
                            <div>reviewed by:- <EmployeeMinDetailCard {...jobApplication.reviewedBy} /></div>
                            <div>remark:- <p>{jobApplication.remark}</p></div>
                        </div>
                    }
                    <div className="flex flex-wrap gap-1">
                        <Link to={"/jobs/job-applications/"+jobApplication.id}><Button>Details</Button></Link>
                        <DocViewer url={'/api/resource/cvs/' + jobApplication.cvPath} />
                    </div>

                </CardContent>
            </Card>
        </>
    )
}

export default JobApplicationListCard