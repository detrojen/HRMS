import { useMutation } from "@tanstack/react-query";
import { requestSlot } from "../services/game-scheduling.service";
import { useNavigate } from "react-router-dom";

const useRequestSlotMutation = () => {
    const navTo = useNavigate()
    return useMutation(
    {
        mutationFn: (payload: { slotId: number; otherPlayersId: number[]; })=>requestSlot(payload),
        onSuccess: (data)=>{
            navTo("/game")
        }
    }
)
}

export default useRequestSlotMutation