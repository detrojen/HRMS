import type { TPageableProps } from "@/types/propsTypes/TPageableProps.type"
import { Button } from "./button"
import { useSearchParams } from "react-router-dom"
import { Input } from "./input"

function Pageable<T>({data,render}:TPageableProps<T>){
    const [searchParams,setSearchparams] = useSearchParams()
    return (
        <>
            {
                data.content.map(render)
            }
            <div className="flex w-1/1 gap-1.5 justify-center">
            <Button
                    disabled={data.first} 
                    variant={"outline"} onClick={()=>{
                    setSearchparams({page: (Number(searchParams.get("page"))-1).toString()})
                }}>previous</Button>
                <Button variant={"outline"}
                    disabled={data.last}
                    onClick={()=>{
                    setSearchparams({page: (Number(searchParams.get("page"))+1).toString()})
                }}>next</Button>
                
            </div>
        </>
    )
}

export default Pageable