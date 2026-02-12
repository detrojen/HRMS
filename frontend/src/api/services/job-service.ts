import type { TCreateJobRequest } from "@/types/apiRequestTypes/TCreateJobRequest.type";
import api from "../api";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";
import type { TPageable } from "@/types/TPageable.ttype";
import type { TJobResponse } from "@/types/apiResponseTypes/TJobResponse.type";

export const createJobRequest = (payload: TCreateJobRequest) => {
    const formData = new FormData()
    formData.append("jobDetail",new Blob([JSON.stringify(payload.jobDetail)], {type:"application/json"}))
    formData.append("jobDocument",payload.jdDocument)
    // formData.append("jdDocument" ,payload.jobDetail.jdDocument)
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
    return api.get<TGlobalResponse<TPageable<TJobResponse>>>(url)
}

export const fetchJobById = (jobId:number) => {
    return api.get<TGlobalResponse<TJobResponse>>(`/api/jobs/${jobId}`)
}