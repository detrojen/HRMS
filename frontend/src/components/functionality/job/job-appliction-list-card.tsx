import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import type { TJobApplicationResponse } from "@/types/apiResponseTypes/TJobApplicationResponse.type"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import DocViewer from "@/components/ui/doc-viewer"
import { Button } from "@/components/ui/button"

const JobApplicationListCard = (jobApplication:TJobApplicationResponse) => {
    return (
        <>
            <Card>
                <CardHeader>
                    <CardTitle>Applied for {jobApplication.job.title}</CardTitle>
                </CardHeader>
                <CardContent>
                    <p>Applicants details:-</p>
                    <li>
                        <ul>Name: - {jobApplication.applicantsName}</ul>
                        <ul>email: - {jobApplication.applicantsEmail}</ul>
                        <ul>phone: - {jobApplication.applicantsPhone}</ul>
                    </li>
                    <div className="my-5">
                        refered by:- <EmployeeMinDetailCard firstName={jobApplication.referedBy.firstName} lastName={jobApplication.referedBy.lastName} designation={jobApplication.referedBy.designation} />
                    </div>
                    <div className="flex flex-wrap gap-1">
                        <Button>Details</Button>
                        <DocViewer url={'/api/resource/cvs/'+jobApplication.cvPath} />
                    </div>
                </CardContent>
            </Card>
        </>
    )
}

export default JobApplicationListCard