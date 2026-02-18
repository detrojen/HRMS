import { useQuery } from "@tanstack/react-query";
import { fetchAssignedTravels, fetchAssignedTravelsOfEmployee, fetchExpenseAsHR, fetchTravelById } from "../services/travel.service";
import type { TTypeMinDetails } from "@/types/apiResponseTypes/TTravelMinDetails.type";
import type { TTravelExpenseQueryParams } from "@/types/apiRequestTypes/TTravelExpenseQueryParams.type";

export const useFetchAssignedTravels = () => useQuery(
    {
        queryKey:["assigned-travels"],
        queryFn: ()=>fetchAssignedTravels()
    }
)

export const useFetchAssignedTravelsOfEmployee = () => useQuery(
    {
        queryKey:["assigned-travels-as-manager"],
        queryFn: ()=>fetchAssignedTravelsOfEmployee()
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