import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import api from "../api"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"

export const getEmployeesByNameQuery = (nameLike:string) => {
    return nameLike.trim() == "" ? []:api.get<TGlobalResponse<TEmployeeWithNameOnly[]>>(`/api/employees?nameLike=${nameLike.trim()}`).then(res=>res.data)
}