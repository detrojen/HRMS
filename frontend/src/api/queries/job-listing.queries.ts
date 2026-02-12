import { useQuery } from "@tanstack/react-query";
import { fetchJobById, fetchJobs } from "../services/job-service";

export const useFetchJobs = (page:number|null,limit:number|null) => useQuery({
    queryKey: ["jobs",`job-page-${page??0}`,`job-limit-${limit??5}`],
    queryFn: () => fetchJobs(page,limit)
})
export const useFetchJobById = (jobId:number) => useQuery({
    queryKey: ["jobs",`job-id-${jobId}`],
    queryFn: () => fetchJobById(jobId)
})