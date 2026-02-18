import { useQuery } from "@tanstack/react-query"
import { fetchNotifications } from "../services/notification.service"

export const useNotifications = () => useQuery(
    {
        queryKey: ["notification"],
        queryFn:()=> fetchNotifications(),
        staleTime:0,
        refetchInterval: 60* 1000 * 5,
    }
)
