import { useMutation } from "@tanstack/react-query";
import { createGameType } from "../services/game-scheduling.service";
import type { TGameType } from "@/types/apiResponseTypes/TGameType.type";

const useCreategameTypeMutation = () => useMutation(
    {
        mutationFn: (payload:Omit<TGameType,"id">)=>createGameType(payload)
    }
)
export default useCreategameTypeMutation