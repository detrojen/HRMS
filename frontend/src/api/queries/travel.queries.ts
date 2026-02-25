import { useQuery } from "@tanstack/react-query";
import { fetchExpenseAsHR, fetchTravelById, fetchTravels } from "../services/travel.service";
import type { TTravelMinDetail } from "@/types/apiResponseTypes/TTravelMinDetails.type";
import type { TTravelExpenseQueryParams } from "@/types/apiRequestTypes/TTravelExpenseQueryParams.type";

export const useFetchTravels = (getAsa:string) => useQuery(
    {
        queryKey:["travels", getAsa],
        queryFn: ()=>fetchTravels(getAsa)
    }
)


export const useFetchTravelById = (travelId:string) => useQuery(
    {
        queryKey:["travel-by-id", "travel-by-id"+travelId],
        queryFn: ()=>fetchTravelById(travelId)
    }
)


export const useFetchExpenseAsHR = (params:TTravelExpenseQueryParams) => useQuery(
    {
        queryKey:["travel-expense", "travel-expense-by-id"+params.travelId, params],
        queryFn: ()=>fetchExpenseAsHR(params),
        
    }
)