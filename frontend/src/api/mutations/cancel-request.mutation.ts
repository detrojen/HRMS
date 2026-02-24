import { useMutation } from "@tanstack/react-query";
import { cancelRequestedSlot } from "../services/game-scheduling.service";
import { useNavigate } from "react-router-dom";

const useCancelSlotMutation = () => {
    const navTo = useNavigate()
    return useMutation(
    {
        mutationFn: (slotRequestId:number)=>cancelRequestedSlot(slotRequestId),
        onSuccess:(data)=>{
            if(data.status === "OK"){
                navTo("/game")
            }
        }
        
    }
)
}
export {useCancelSlotMutation}