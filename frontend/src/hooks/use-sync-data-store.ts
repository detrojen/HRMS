import { useAppDispatch } from "@/store/hooks";
import { useQuery, type UseQueryOptions } from "@tanstack/react-query";

import { useEffect } from "react";

const useSyncDataStore = <T>(queryOptions: UseQueryOptions<T> &{ action : (data:T)=>any | undefined}) =>{
    const dispatch = useAppDispatch()
    const query = useQuery<T>(
        {...queryOptions}
    )
    useEffect(
        ()=>{
            if(query.data && queryOptions.action){
                dispatch(queryOptions.action(query.data))
            }
            
        },[query.data]
    )
    return query
}

export default useSyncDataStore