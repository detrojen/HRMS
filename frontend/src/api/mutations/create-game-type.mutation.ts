import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createGameType } from "../services/game-scheduling.service";
import { useNavigate } from "react-router-dom";
import type { TCreateUpdateGameTypeRequest } from "@/types/apiRequestTypes/TCreateUpdateGameTypeRequest.type";

const useCreategameTypeMutation = () => {
    const navTo = useNavigate()
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload:TCreateUpdateGameTypeRequest)=>createGameType(payload)
        ,onSuccess: (data) => {
            if(data.data.status == "OK"){
                queryClient.invalidateQueries({queryKey: ["game-interest"]})
                navTo("/game")
            }
        }
    }
)}
export default useCreategameTypeMutation