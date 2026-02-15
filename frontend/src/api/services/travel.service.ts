import api from "../api";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";
import type { TCreateTravelRequest } from "@/types/apiRequestTypes/TcreateTravelRequest.type";

export const createTravel = (payload:TCreateTravelRequest) => {
    return api.post<TGlobalResponse<any>>("/api/travels",payload)
}