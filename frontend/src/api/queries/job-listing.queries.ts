import { useQuery } from "@tanstack/react-query";
import { fetchJobById, fetchJobs, getJobApplicationById, jobApplications, referedJobApplication } from "../services/job-service";

export const useFetchJobs = (page:number|null,limit:number|null) => useQuery({
    queryKey: ["jobs",`job-page-${page??0}`,`job-limit-${limit??5}`],
    queryFn: () => fetchJobs(page,limit)
})
export const useFetchJobById = (jobId:number) => useQuery({
    queryKey: ["jobs",`job-id-${jobId}`],
    queryFn: () => fetchJobById(jobId)
})

export const useFetchJobApplications = (page?:number|null,limit?:number|null) => useQuery({
    queryKey: ["job-applications",page,limit],
    queryFn: () => jobApplications(page,limit)
})
export const useFetchReferedJobApplications = (page?:number|null,limit?:number|null) => useQuery({
    queryKey: ["job-applications","refered-job-application",page,limit],
    queryFn: () => referedJobApplication(page,limit)
})

export const useFetchJobApplicationById = (jobApplicationId: number) => {
    return useQuery({
        queryKey:["job-applications",`job-application-by-id-${jobApplicationId}`]
        ,queryFn:()=>getJobApplicationById(jobApplicationId)
    })
}