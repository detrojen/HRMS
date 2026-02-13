import { useQuery } from "@tanstack/react-query"
import { getEmployeesByNameQuery } from "../services/employee.service"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"

export const useGetchEmployeesByNameLike = (nameQuery:string) => {
    return useQuery(
        {
            queryKey: ["employees-like",nameQuery],
            queryFn: ()=>getEmployeesByNameQuery(nameQuery)
        }
    )
}
