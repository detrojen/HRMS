import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { createTravel } from "../services/travel.service";
import type { TCreateTravelRequest } from "@/types/apiRequestTypes/TcreateTravelRequest.type";

const useCreateTravelMutation = () => {
    const navTo = useNavigate()
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload: TCreateTravelRequest)=>createTravel(payload)
        ,onSuccess: (data) => {
            if(data.status == "OK"){
                queryClient.invalidateQueries({queryKey: ["travel-by-id","travles","travel-min-detail-by-id"]})
                navTo("/travels/manage")
            }
        }
    }
)}
export default useCreateTravelMutation