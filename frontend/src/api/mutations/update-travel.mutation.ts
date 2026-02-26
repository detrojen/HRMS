import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import {  updateTravel } from "../services/travel.service";
import type { TCreateTravelRequest } from "@/types/apiRequestTypes/TcreateTravelRequest.type";

const useUpdateTravelMutation = () => {
    const navTo = useNavigate()
    const queryClient = useQueryClient()
    return useMutation(
    {
        mutationFn: (payload: TCreateTravelRequest)=>updateTravel(payload)
        ,onSuccess: (data) => {
            if(data.status == "OK"){
                queryClient.invalidateQueries({queryKey: ["travel-by-id","travles","travel-min-detail-by-id"]})
                navTo("/travels/manage")
            }
        }
    }
)}
export default useUpdateTravelMutation