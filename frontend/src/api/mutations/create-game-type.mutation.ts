import { useMutation, useQueryClient } from "@tanstack/react-query";
import { createGameType } from "../services/game-scheduling.service";
import type { TGameType } from "@/types/apiResponseTypes/TGameType.type";
import { useNavigate } from "react-router-dom";

const useCreategameTypeMutation = () => {
    const navTo = useNavigate()
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload:Omit<TGameType,"id">)=>createGameType(payload)
        ,onSuccess: (data) => {
            if(data.status == "OK"){
                queryClient.invalidateQueries({queryKey: ["game-interest"]})
                navTo("/game")
            }
        }
    }
)}
export default useCreategameTypeMutation