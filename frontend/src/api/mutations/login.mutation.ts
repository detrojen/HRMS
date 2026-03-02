import { useMutation, useQueryClient } from "@tanstack/react-query"
import login from "../services/login.service"
import type { TLoginPayload } from "@/types/apiRequestTypes/TLoginPayload.type"
import { useNavigate } from "react-router-dom"
import errorHandler from "@/apiErrorHandler"

const useLoginMutation = () => {
  const navTo = useNavigate()
  const queryClient = useQueryClient()
  return useMutation({
    mutationFn: (data: TLoginPayload) => {
      return login(data)
    },
    onSuccess: (data) => {
      debugger
      if (data.data.status === "OK") {
        queryClient.invalidateQueries()
        localStorage.setItem("HRMS_AUTH_TOKEN", data?.data.data.jwtToken!)
        navTo("/")
      }else{
        errorHandler(data.data)
      }
    },
    onError: (data) => {
      console.table(data)
    }
  })
}

export default useLoginMutation