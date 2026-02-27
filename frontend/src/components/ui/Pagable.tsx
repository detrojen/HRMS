import type { TPageableProps } from "@/types/propsTypes/TPageableProps.type"
import { Button } from "./button"
import { useSearchParams } from "react-router-dom"
import { Input } from "./input"
import type { HtmlHTMLAttributes } from "react"

function Pageable<T>({data,render,className}:TPageableProps<T>&HtmlHTMLAttributes<HTMLDivElement>){
    const [searchParams,setSearchparams] = useSearchParams()
    return (
        <>
           <div className={className}>
             {
                data.content.map(render)
            }
           </div>
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