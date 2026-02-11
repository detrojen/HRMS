import { useMutation } from "@tanstack/react-query";
import { cancelRequestedSlot } from "../services/game-scheduling.service";

const useCancelSlotMutation = () => {
    return useMutation(
    {
        mutationFn: (slotRequestId:number)=>cancelRequestedSlot(slotRequestId),
        
        
    }
)
}
export {useCancelSlotMutation}