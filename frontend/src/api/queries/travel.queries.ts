import { useQuery } from "@tanstack/react-query";
import { fetchExpenseAsHR, fetchTraveExpensecategories, fetchTravelById, fetchTravelMinDetailById, fetchTravels } from "../services/travel.service";
import type { TTravelExpenseQueryParams } from "@/types/apiRequestTypes/TTravelExpenseQueryParams.type";

export const useFetchTravels = (getAsa:string,params) => useQuery(
    {
        queryKey:["travels", getAsa,params],
        queryFn: ()=>fetchTravels(getAsa,params)
    }
)


export const useFetchTravelById = (travelId:string) => useQuery(
    {
        queryKey:["travel-by-id", "travel-by-id"+travelId],
        queryFn: ()=>fetchTravelById(travelId)
    }
)
export const useFetchTravelMinDetailsById = (travelId:string) => useQuery(
    {
        queryKey:["travel-min-detail-by-id", "travel-min-detail-by-id"+travelId],
        queryFn: ()=>travelId==="0"?null:fetchTravelMinDetailById(travelId)
    }
)


export const useFetchExpenseAsHR = (params:TTravelExpenseQueryParams) => useQuery(
    {
        queryKey:["travel-expense", "travel-expense-by-id"+params.travelId, params],
        queryFn: ()=>fetchExpenseAsHR(params),
        
    }
)

export const useFetchTraveExpensecategories = () => {
    return useQuery(
        {
            queryKey: ["expense-categories"]
            ,queryFn: () => fetchTraveExpensecategories()
        }
    )
}

