import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type";
import api from "../api";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";
import type { TCreateTravelRequest } from "@/types/apiRequestTypes/TcreateTravelRequest.type";
import type { TTravelDetails } from "@/types/apiResponseTypes/TTravelDetails.type";
import type { TTypeMinDetails } from "@/types/apiResponseTypes/TTravelMinDetails.type";
import type { TAddUpdateExpense } from "@/types/apiRequestTypes/TAddUpdateExpense.type";

export const createTravel = (payload: TCreateTravelRequest) => {
    return api.post<TGlobalResponse<any>>("/api/travels", payload)
}

export const fetchAssignedTravels = () => {
    return api.get<TGlobalResponse<TTypeMinDetails>>("/api/travels/assigned-travels")
}


export const fetchTravelById = (travelId: string) => {
    return api.get<TGlobalResponse<TTravelDetails>>("/api/travels/" + travelId)
}

export const uploadTraveldocumnet = (payload: TUploadTravelDocumnetRequest) => {
    const formData = new FormData()
    formData.append("documentDetails", new Blob([JSON.stringify(payload.documentDetails)], { type: "application/json" }))
    formData.append("file", payload.file)

    return api.post(`/api/travels/${payload.travelId}/documents`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
}


export const uploadEmployeeTraveldocumnet = (payload: TUploadTravelDocumnetRequest) => {
    const formData = new FormData()
    formData.append("documentDetails", new Blob([JSON.stringify(payload.documentDetails)], { type: "application/json" }))
    formData.append("file", payload.file)

    return api.post(`/api/travels/${payload.travelId}/employee-documents`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
}

export const addExpense = (payload: TAddUpdateExpense & { travelId: string | number }) => {
    const formData = new FormData()
    formData.append("expenseDetails", new Blob([JSON.stringify(payload.expenseDetails)], { type: "application/json" }))
    formData.append("file", payload.file!)

    return api.post(`/api/travels/${payload.travelId}/expenses`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
}
export const updateExpense = (payload: TAddUpdateExpense & { travelId: string | number }) => {
    const formData = new FormData()
    formData.append("expenseDetails", new Blob([JSON.stringify(payload.expenseDetails)], { type: "application/json" }))
    if (payload.file) { formData.append("file", payload.file??null) }

    return api.put(`/api/travels/${payload.travelId}/expenses`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
}