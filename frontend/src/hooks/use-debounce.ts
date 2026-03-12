import { useEffect, useRef, useState } from "react"

function useDebounce<T>(initialData:T,debounceTime:number, debounceAction?:(data:T)=>void):[T,T, React.Dispatch<React.SetStateAction<T>>]{
    const [data,setData] = useState<T>(initialData)
    const [debouncedState,setDebouncedState] = useState<T>(data)
    const debounceRef = useRef(0)
    useEffect(()=>{
        if(debounceRef.current>0){
            clearTimeout(debounceRef.current)
        }
        debounceRef.current = setTimeout(()=>{
            const localData = data
            setDebouncedState( localData)
            if(debounceAction!=undefined){
                debounceAction(localData)
            }
        },debounceTime)
    },[data])

    return [data,debouncedState,setData]
}

export default useDebounce