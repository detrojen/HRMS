import { useEffect, useRef, useState } from "react"

function useDebounce<T>(data:T,debounceTime:number){
    const [debouncedState,setDebouncedState] = useState<T>(data)
    const debounceRef = useRef(0)
    useEffect(()=>{
        if(debounceRef.current>0){
            clearTimeout(debounceRef.current)
        }
        debounceRef.current = setTimeout(()=>{setDebouncedState(data)},debounceTime)
    },[data])
    return debouncedState
}

export default useDebounce