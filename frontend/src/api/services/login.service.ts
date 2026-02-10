import axios from "axios";
import type { TLoginPayload } from "@/types/apiRequestTypes/TLoginPayload.type";
import type { TGlobalResponse } from "@/types/apiResponseTypes/TGlobalResponse.type";
import type { TLoginResponse } from "@/types/apiResponseTypes/TLoginResponse.type";
import errorHandler from "@/apiErrorHandler";

const  login =async (data:TLoginPayload) => {
    
    const axiosInstence = axios.create();
    return await axiosInstence.post<TGlobalResponse<TLoginResponse>>("/api/auth/login",data).then(res=>res.data)
    .catch(function (error) {
         errorHandler(error.response)
  });
}

export default login