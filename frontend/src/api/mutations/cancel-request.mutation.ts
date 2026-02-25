import { useMutation, useQueryClient } from "@tanstack/react-query";
import { cancelRequestedSlot } from "../services/game-scheduling.service";
import { useNavigate } from "react-router-dom";

const useCancelSlotMutation = () => {
    const navTo = useNavigate()
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (slotRequestId:number)=>cancelRequestedSlot(slotRequestId),
        onSuccess:(data)=>{
            if(data.status === "OK"){
                navTo("/game")
                queryClient.invalidateQueries({queryKey:["active-slots"]})
            }
        }
        
    }
)
}
export {useCancelSlotMutation}