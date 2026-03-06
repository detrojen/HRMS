import type { TUpdateGameInterest } from "@/types/apiRequestTypes/TUpdateGameInterest.type";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateGameInterest } from "../services/game-scheduling.service";

const useUpdateGameInterestMutation = () =>{
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload: TUpdateGameInterest) => updateGameInterest(payload),
        onSuccess: (data)=>{
            if(data.data.status === "OK"){
                queryClient.invalidateQueries({queryKey:["game-interest"]})
            }
        }
    }
)}

export default useUpdateGameInterestMutation