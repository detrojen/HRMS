import { useState, type PropsWithChildren, type ReactNode } from "react"
import { Popover, PopoverContent, PopoverTrigger } from "./popover"
import { ScrollArea } from "./scroll-area"
import { Checkbox } from "./checkbox"
import { Input } from "./input"

type TMultiSelectDropDownProps<T> = {
    data: T[]
    onSelectChange: (value: boolean,data:T)=>void
    render: (data:T)=>ReactNode
    placeHolder: string
    showInputValue : (item:T)=>string
    defaultValue?: T[]
}
function MultiSelectDropDown<T>({defaultValue,showInputValue,placeHolder,children,className,data,onSelectChange,render}:TMultiSelectDropDownProps<T>&PropsWithChildren& React.HTMLAttributes<HTMLDivElement>){
   const [open,setOpen] = useState<boolean>(false)
   const [selected,setSelected] = useState<T[]>(defaultValue??[])
    return (
        <Popover open={open} onOpenChange={()=>{setOpen(!open)}}>
            <PopoverTrigger asChild>
                <div 
                    className={className}
                    onClick={()=>{setOpen(!open)}}
                >
                    <Input placeholder={placeHolder} value={selected.length>0?showInputValue(selected[0]):""}/>
                    {children}
                </div>
                   
                </PopoverTrigger>
            <PopoverContent className="top-500 w-100" align="start">
            
                 <ScrollArea className="overflow-y-scroll max-h-100">
                    {
                        data.map(item=>(
                            <div className="flex gap-2">
                                <Checkbox checked={selected.includes(item)} onCheckedChange={(value)=>{
                                    onSelectChange(value===true,item);
                                    if(value===true){
                                        setSelected([...selected,item])
                                    }else{
                                        setSelected(selected.filter(i=>i!=item))
                                    }
                                }}/> {render(item)}
                            </div>
                        ))
                    }

                </ScrollArea>
            </PopoverContent>
        </Popover>
    )
}

export default MultiSelectDropDown