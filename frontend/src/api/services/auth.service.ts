import type{ TGlobalResponse } from "@/types/TGlobalResponse.type"
import api from "../api"
import type { TSelfResponse } from "@/types/apiResponseTypes/TSelfResponse.type"

export const switchRole = (roleId: number) => {
    return api.post<TGlobalResponse<TSelfResponse>>("/api/auth/switch-role/"+roleId)
}