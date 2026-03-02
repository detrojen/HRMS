import axios from "axios";
import type { TLoginPayload } from "@/types/apiRequestTypes/TLoginPayload.type";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";
import type { TLoginResponse } from "@/types/apiResponseTypes/TLoginResponse.type";

const  login =async (data:TLoginPayload) => {
    
    const axiosInstence = axios.create();
    return axiosInstence.post<TGlobalResponse<TLoginResponse>>("/api/auth/login",data)
}

export default login