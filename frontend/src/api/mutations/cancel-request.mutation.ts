import { useMutation, useQueryClient } from "@tanstack/react-query";
import { cancelRequestedSlot } from "../services/game-scheduling.service";

const useCancelSlotMutation = () => {
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (slotRequestId:number)=>cancelRequestedSlot(slotRequestId),
        onSuccess:(data)=>{
            debugger
            if(data.data.status === "OK"){
                queryClient.invalidateQueries({queryKey:["requested-slot-detail"]})
                queryClient.invalidateQueries({queryKey:["slot-request-history"]})
                
            }
        }
    }
)
}
export {useCancelSlotMutation}