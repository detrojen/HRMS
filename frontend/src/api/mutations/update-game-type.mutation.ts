import { useMutation, useQueryClient } from "@tanstack/react-query";
import { updateGameType } from "../services/game-scheduling.service";
import { useNavigate } from "react-router-dom";
import type { TCreateUpdateGameTypeRequest } from "@/types/apiRequestTypes/TCreateUpdateGameTypeRequest.type";

const useUpdategameTypeMutation = () => {
    const queryClient = useQueryClient()
    const navTo = useNavigate()
    return useMutation(
        {
            mutationFn: (payload: TCreateUpdateGameTypeRequest) => {

                return updateGameType(payload)
            }
            , onSuccess: (data) => {
                if (data.data.status === "OK") {
                    queryClient.invalidateQueries({ queryKey: ["game-interest", "game-types"] })
                    navTo("/game")
                }
            }
        }
    )
}
export default useUpdategameTypeMutation