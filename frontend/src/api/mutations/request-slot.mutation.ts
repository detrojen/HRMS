import { useMutation, useQueryClient } from "@tanstack/react-query";
import { requestSlot } from "../services/game-scheduling.service";
import { useNavigate } from "react-router-dom";

const useRequestSlotMutation = () => {
    const navTo = useNavigate()
    const queryClient = useQueryClient()
    return useMutation(
        {
            mutationFn: (payload: { slotId: number; otherPlayersId: number[]; }) => requestSlot(payload),
            onSuccess: (data) => {
                debugger
                if (data.data.status === "OK") {
                    navTo("/game")
                    queryClient.invalidateQueries({ queryKey: ["slot-request-history"] })
                }

            }
        }
    )
}

export default useRequestSlotMutation