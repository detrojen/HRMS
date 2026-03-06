import { useQuery } from "@tanstack/react-query"
import { fetchHrList, getEmployeesByNameQuery, getOneLevelReportOrgChart } from "../services/employee.service"
export const useGetchEmployeesByNameLike = (nameQuery:string) => {
    return useQuery(
        {
            queryKey: ["employees-like",nameQuery],
            queryFn: ()=>getEmployeesByNameQuery(nameQuery),
            enabled: nameQuery.trim().length > 0
        }
    )
}

export const useFetchOrgChart = (employeeId:string | null) => {
    return useQuery(
        {
            queryKey: ["orgchart", `orgchart-${employeeId}`],
            queryFn: ()=>getOneLevelReportOrgChart(employeeId),
            select:(data)=>data.data.data
        }
    )
}

export const useFetchHrList = () => {
    return useQuery(
        {
            queryKey: ["hr-list"],
            queryFn: ()=>fetchHrList()
        }
    )
}
