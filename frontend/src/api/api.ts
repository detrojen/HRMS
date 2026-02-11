import errorHandler from "@/apiErrorHandler";
import axios from "axios";
import { toast } from "sonner";

const axiosInstence = axios.create({
  // baseURL: "http://localhost:8080",
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
    if(response.data.message){
      toast(response.data.message)
    }
    return response.data;
  },(error)=>{
    
    errorHandler(error.response);
    return error.response
  }
)
export default axiosInstence;