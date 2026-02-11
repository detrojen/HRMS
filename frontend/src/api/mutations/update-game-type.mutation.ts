import { useMutation } from "@tanstack/react-query";
import { updateGameType } from "../services/game-scheduling.service";
import type { TGameType } from "@/types/apiResponseTypes/TGameType.type";

const useUpdategameTypeMutation = () => {
    debugger
    return useMutation(
    {
        mutationFn:  (gameTypeId:string,payload:any)=> { 
            debugger
            return updateGameType(gameTypeId,payload)}
    }
)
}
export default useUpdategameTypeMutation