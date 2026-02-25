import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateGameType } from "../services/game-scheduling.service";
import type { TGameType } from "@/types/apiResponseTypes/TGameType.type";
import { useNavigate } from "react-router-dom";

const useUpdategameTypeMutation = () => {
    const queryClient = useQueryClient()
    const navTo = useNavigate()
    return useMutation(
        {
            mutationFn: (payload: TGameType) => {

                return updateGameType(payload)
            }
            , onSuccess: (data) => {
                debugger
                if (data.status == "OK") {
                    queryClient.invalidateQueries({ queryKey: ["game-interest", "game-types"] })
                    navTo("/game")
                }
            }
        }
    )
}
export default useUpdategameTypeMutation