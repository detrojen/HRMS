import { useQuery } from "@tanstack/react-query"
import { getEmployeesByNameQuery, getOneLevelReportOrgChart } from "../services/employee.service"
export const useGetchEmployeesByNameLike = (nameQuery:string) => {
    return useQuery(
        {
            queryKey: ["employees-like",nameQuery],
            queryFn: ()=>getEmployeesByNameQuery(nameQuery)
        }
    )
}

export const useFetchOrgChart = (employeeId:string | null) => {
    return useQuery(
        {
            queryKey: ["orgchart", `orgchart-${employeeId}`],
            queryFn: ()=>getOneLevelReportOrgChart(employeeId)
        }
    )
}
