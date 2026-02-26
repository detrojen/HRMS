import errorHandler from "@/apiErrorHandler";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";
import axios from "axios";
import { toast } from "sonner";

const axiosInstence = axios.create({
  headers: {
    "Content-type": "application/json"
  }
})
axiosInstence.defaults.headers.post['Access-Control-Allow-Origin'] = '*';
axiosInstence.interceptors.request.use((config)=>{
    const authToken = localStorage.getItem("HRMS_AUTH_TOKEN");
    if(authToken){
        config.headers.Authorization = `Bearer ${authToken}`
    }
    return config
}, (error)=>{})

axiosInstence.interceptors.response.use((response)=>{
    if(response.headers["authorization"]){
      debugger
      localStorage.setItem("HRMS_AUTH_TOKEN",response.headers["authorization"])
      // localStorage.setItem("HRMS_AUTH_TOKEN",response.headers.getAuthorization.toString())
    }
    if(response.data.message){
      toast(response.data.message)
    }
    return response.data ;
  },(error)=>{
    
    errorHandler(error.response.data);
    return error.response
  }
)
export default axiosInstence;