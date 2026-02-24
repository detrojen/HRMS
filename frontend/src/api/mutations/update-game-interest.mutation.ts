import type { TUpdateGameInterest } from "@/types/apiRequestTypes/TUpdateGameInterest.type";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateGameInterest } from "../services/game-scheduling.service";

const updateGameInterestMutation = () =>{
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload: TUpdateGameInterest) => updateGameInterest(payload),
        onSuccess: (data)=>{
            queryClient.invalidateQueries({queryKey:["game-interest"]})
        }
    }
)}

export default updateGameInterestMutation