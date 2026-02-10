import { useNavigate } from "react-router-dom";
import  { type TGlobalResponse } from "./types/apiResponseTypes/TGlobalResponse.type";
import { toast } from "sonner";

const errorHandler = (error:TGlobalResponse<any>) => {
    
    console.log(error)
    if(error.status == 401){
        debugger
        window.location.assign("/login")
        toast("please login again")
    }else{
        toast(error.data.message)
    }
}

export default errorHandler