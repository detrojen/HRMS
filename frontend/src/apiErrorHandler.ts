import { useNavigate } from "react-router-dom";
import  { type TGlobalResponse } from "./types/TGlobalResponse.type";
import { toast } from "sonner";

const errorHandler = (error:TGlobalResponse<any>) => {
    
    console.log(error)
    if(error.status == 401 || error.status === "UNAUTHORIZED"){
        localStorage.removeItem("HRMS_AUTH_TOKEN")
        window.location.assign("/login")
        toast("please login again")
    }else if(error.status == 400 || error.status === "BAD_REQUEST"){
        error.errors.forEach(error=>{
            toast(error.message)
        })
    }
}

export default errorHandler