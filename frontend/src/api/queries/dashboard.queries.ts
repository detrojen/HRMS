import { useQuery } from "@tanstack/react-query"
import { getDashboardData } from "../services/dashboard.service"

export const useFetchDashboardData = () => {
    return useQuery({
        queryKey: ["travel-details","game-slots","active-slots"]
        ,queryFn: () => getDashboardData()
    })
}