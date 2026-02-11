import type { TUpdateGameInterest } from "@/types/apiRequestTypes/TUpdateGameInterest.type";
import { useMutation } from "@tanstack/react-query";
import { updateGameInterest } from "../services/game-scheduling.service";

const updateGameInterestMutation = () => useMutation(
    {
        mutationFn: (payload: TUpdateGameInterest) => updateGameInterest(payload),
        onSuccess: (data)=>{
            console.log(data)
        }
    }
)

export default updateGameInterestMutation