import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import api from "../api"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"
import type{ TEmployeeMinDetail } from "@/types/TEmployeeMinDetail.type"

export const getEmployeesByNameQuery = (nameLike:string) => {
    return nameLike.trim() === "" ? null : api.get<TGlobalResponse<TEmployeeWithNameOnly[]>>(`/api/employees?nameLike=${nameLike.trim()}`)
}


export const getOneLevelReportOrgChart = (employeeId : string | null) => {
    const param = employeeId != null? `?employeeId=${employeeId}`:""
    return api.get<TGlobalResponse<any>>("/api/employees/one-level-report"+param)
}

export const fetchHrList = () => {
    return api.get<TGlobalResponse<TEmployeeMinDetail[]>>("/api/employees/role_hr")
}
