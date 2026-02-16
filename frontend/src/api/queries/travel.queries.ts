import { useQuery } from "@tanstack/react-query";
import { fetchAssignedTravels, fetchTravelById } from "../services/travel.service";
import type { TTypeMinDetails } from "@/types/apiResponseTypes/TTravelMinDetails.type";

export const useFetchAssignedTravels = () => useQuery(
    {
        queryKey:["assigned-travels"],
        queryFn: ()=>fetchAssignedTravels()
    }
)

export const useFetchTravelById = (travelId:string) => useQuery(
    {
        queryKey:["travel-by-id", "travel-by-id"+travelId],
        queryFn: ()=>fetchTravelById(travelId)
    }
)