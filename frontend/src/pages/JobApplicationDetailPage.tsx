import { useFetchJobApplicationById } from "@/api/queries/job-listing.queries"
import EmployeeMinDetailCard from "@/components/functionality/employee-min-detail-card"
import ReviewCvAction from "@/components/functionality/job/review-cv-action"
import ReviewJobApplicationAction from "@/components/functionality/job/review-job-application-action"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card"
import DocViewer from "@/components/ui/doc-viewer"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { useContext } from "react"
import { useParams } from "react-router-dom"

const JobApplicationDetailPage = () => {
    const { user } = useContext(AuthContext)
    const { jobApplicationId } = useParams()
    const { data, isLoading, isError } = useFetchJobApplicationById(Number(jobApplicationId))
    const jobApplication = data?.data
    if (data && data?.status != "OK") {
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
                    <div className="my-5">
                        
                        <Table className="border">
                            
                            <TableHeader>
                                <TableRow>
                                    <TableHead colSpan={2} className=" border text-center">
                                        Cv Reviews
                                    </TableHead >
                                </TableRow>
                                <TableRow>
                                    <TableHead className="text-center border">
                                        Reviewed By
                                    </TableHead >
                                    <TableHead  className="text-center border">
                                        Review
                                    </TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {jobApplication.cvReviews.map(review => (
                                    <TableRow>
                                        <TableCell className="text-center border">{review.reviewedBy.firstName + " " + review.reviewedBy.lastName}</TableCell>
                                        <TableCell className="text-center border">{review.review}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
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
                        {jobApplication.job.reviewers.some(reviewer=>reviewer.reviewer.id == user.id) &&<ReviewCvAction jobApplicationId={jobApplication.id} />}
                    </div>

                </CardContent>
            </Card>
        </>
    )
}
export default JobApplicationDetailPage