import { useFetchJobApplicationById } from "@/api/queries/job-listing.queries"
import EmployeeMinDetailCard from "@/components/functionality/employee-min-detail-card"
import ReviewJobApplicationAction from "@/components/functionality/job/review-job-application-action"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card"
import DocViewer from "@/components/ui/doc-viewer"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { useContext } from "react"
import { useParams } from "react-router-dom"

const JobApplicationDetailPage = () => {
    const { user } = useContext(AuthContext)
    const { jobApplicationId } = useParams()
    const { data, isLoading,isError } = useFetchJobApplicationById(Number(jobApplicationId))
    const jobApplication = data?.data
    debugger
    if(data && data?.status != "OK"){
        return <h1>You have no access to this page</h1>
    }
    if (isLoading) {
        return "Fetching job application details"
    } if (!jobApplication) {
        return "Error occured"
    }
    return (
        <>
            <Card className="w-1/1">
                <CardHeader>
                    <CardTitle>Applied for {jobApplication.job.title}</CardTitle>
                    <Badge variant={"outline"} className="bg-green-200">{jobApplication.status}</Badge>
                </CardHeader>
                <CardContent>
                    <div className="flex justify-between flex-row-reverse align-top p-5">
                        <div className="my-5">
                            refered by:- <EmployeeMinDetailCard id={jobApplication.referedBy.id} firstName={jobApplication.referedBy.firstName} lastName={jobApplication.referedBy.lastName} designation={jobApplication.referedBy.designation} />
                        </div>
                        <div>
                            <p>Applicants basic details:-</p>
                        <ul>
                            <li>Name: - {jobApplication.applicantsName}</li>
                            <li>email: - {jobApplication.applicantsEmail}</li>
                            <li>phone: - {jobApplication.applicantsPhone}</li>
                        </ul>
                        </div>
                    </div>
                    <div className="my-5">
                        <h1>Other Details</h1>
                        <p>{jobApplication.details}</p>
                    </div>
                    <div className="flex flex-wrap gap-1">
                    </div>

                    {
                        jobApplication.reviewedBy
                        && <div className="flex flex-wrap gap-1 my-5">
                            <div>reviewed by:- <EmployeeMinDetailCard {...jobApplication.reviewedBy} /></div>
                            <div>remark:- <p>{jobApplication.remark}</p></div>
                        </div>
                    }
                    <div className="flex gap-2">
                        <DocViewer url={'/api/resource/cvs/' + jobApplication.cvPath} />
                        {
                            user.role === "HR" &&
                            <>

                                <ReviewJobApplicationAction status="approve" jobApplicationId={jobApplication.id} />
                                <ReviewJobApplicationAction status="reject" jobApplicationId={jobApplication.id} />

                            </>
                        }
                    </div>
                </CardContent>
            </Card>
        </>
    )
}
export default JobApplicationDetailPage