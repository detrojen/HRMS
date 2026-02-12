import { useMutation } from "@tanstack/react-query"
import login from "../services/login.service"
import type { TLoginPayload } from "@/types/apiRequestTypes/TLoginPayload.type"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { TLoginResponse } from "@/types/apiResponseTypes/TLoginResponse.type"
import { useNavigate } from "react-router-dom"

const useLoginMutation = ()=>{
  const navTo = useNavigate()
  return useMutation({
    mutationFn: (data:TLoginPayload) => {
      return  login(data)
    },
    onSuccess:(data: TGlobalResponse<TLoginResponse>)=>{
      localStorage.setItem("HRMS_AUTH_TOKEN",data.data.jwtToken)
      navTo("/")
    },
    onError:(data)=>{
      console.table(data)
    }
  })
}

  export default useLoginMutation