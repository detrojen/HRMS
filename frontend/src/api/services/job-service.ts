import type { TCreateJobRequest } from "@/types/apiRequestTypes/TCreateJobRequest.type";
import api from "../api";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";
import type { TPageableResponse } from "@/types/apiResponseTypes/TPageableResponse.type";
import type { TJobResponse } from "@/types/apiResponseTypes/TJobResponse.type";
import type { TReferJobRequest } from "@/types/apiRequestTypes/TReferJobRequest.type";
import type { TShareJobPayload } from "@/types/apiRequestTypes/TShareJobPayload";
import type { TJobApplicationResponse } from "@/types/apiResponseTypes/TJobApplicationResponse.type";
import type { TReivewJobApplicationRequest } from "@/types/apiRequestTypes/TReivewJobApplicationRequest.type";
import type { TReviewCvRequest } from "@/types/apiRequestTypes/TReviewCvRequest.type";

export const createJobRequest = (payload: TCreateJobRequest) => {
    const formData = new FormData()
    formData.append("jobDetail",new Blob([JSON.stringify(payload.jobDetail)], {type:"application/json"}))
    formData.append("jobDocument",payload.jdDocument)
    return api.post<TGlobalResponse<any>>("/api/jobs",formData,{
        headers:{
            "Content-Type":"multipart/form-data"
        }
    })
}
export const fetchJobs = (page:number|null,limit:number|null) => {
    let url = "/api/jobs?"
    if(page) {
        url+=`page=${page}&`
    }if(limit){
        url+=`limit=${limit}&`
    }
    return api.get<TGlobalResponse<TPageableResponse<TJobResponse>>>(url)
}

export const fetchJobById = (jobId:number) => {
    return api.get<TGlobalResponse<TJobResponse>>(`/api/jobs/${jobId}`)
}

export const referJob = (payload:TReferJobRequest & {jobId: number}) => {
    const formData = new FormData()
    formData.append("applicantsDetail",new Blob([JSON.stringify(payload.basicDetail)], {type:"application/json"}))
    formData.append("cv",payload.cv)
    return api.post<TGlobalResponse<any>>(`/api/jobs/${payload.jobId}/refer`,formData,{
        "headers":{
            "Content-Type" : "multipart/form-data"
        }
    })
}

export const shareJob = (payload:TShareJobPayload) => {
    
    return api.post<TGlobalResponse<any>>(`/api/jobs/${payload.jobId}/share`,payload.data)
}

export const jobApplications = (page?:number|null,limit?:number|null) =>{
    let searchParams = "?"
    if(page) {
        searchParams+=`page=${page}&`
    }if(limit){
        searchParams+=`limit=${limit}&`
    }
    return api.get<TGlobalResponse<TPageableResponse<TJobApplicationResponse>>>("/api/jobs/job-applications"+searchParams)
}
export const referedJobApplication = (page?:number|null,limit?:number|null) =>{
    let searchParams = "?"
    if(page) {
        searchParams+=`page=${page}&`
    }if(limit){
        searchParams+=`limit=${limit}&`
    }
    return api.get<TGlobalResponse<TPageableResponse<TJobApplicationResponse>>>("/api/jobs/refered-job-applications"+searchParams)
}

export const getJobApplicationById = (jobApplicationId : number) => {
    return api.get<TGlobalResponse<TJobApplicationResponse>>("/api/jobs/job-applications/"+jobApplicationId);
}

export const reviewJobApplication = (payload:{review:TReivewJobApplicationRequest,jobApplicationId:number}) => {
    return api.patch("/api/jobs/job-applications/"+ payload.jobApplicationId, payload.review)
}

export const reviewCv = (payload:{review:TReviewCvRequest,jobApplicationId:number}) =>{
    return api.post(`/api/jobs/job-applications/${payload.jobApplicationId}/cv-review`,payload.review)
}