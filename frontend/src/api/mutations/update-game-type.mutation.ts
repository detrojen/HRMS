import { useMutation } from "@tanstack/react-query";
import { updateGameType } from "../services/game-scheduling.service";
import type { TGameType } from "@/types/apiResponseTypes/TGameType.type";

const useUpdategameTypeMutation = () => {
    
    return useMutation(
    {
        mutationFn:  (payload:TGameType)=> { 
            
            return updateGameType(payload)}
    }
)
}
export default useUpdategameTypeMutation