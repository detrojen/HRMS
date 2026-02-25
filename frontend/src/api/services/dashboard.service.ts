import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import api from "../api"
import type { TDashboardDataResponse } from "@/types/apiResponseTypes/TDashboardDataResponse.type"

export const getDashboardData = () => {
    return api.get<TGlobalResponse<TDashboardDataResponse>>("/api/dashboard")
}