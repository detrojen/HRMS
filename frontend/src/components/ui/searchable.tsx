import { Input } from "./input"
import { Popover, PopoverContent, PopoverTrigger } from "./popover"
import { useState, type PropsWithChildren, type ReactNode } from "react"
type TSearchableProps<T> = {
    data: T[],
    setQuery: (value: string) => void
    onSelectItem: (item: T) => void
    render: (item: T) => ReactNode
}
function Searchable<T>({ data, setQuery, render, onSelectItem, children, className}: TSearchableProps<T> & PropsWithChildren & React.HTMLAttributes<HTMLDivElement>) {
    const [open,setOpen] = useState<boolean>(false)
    return (
        <Popover open={open} onOpenChange={()=>{setOpen(!open)}}>
            <PopoverTrigger asChild>
                <div 
                    className={className}
                    onClick={()=>{setOpen(!open)}}
                >
                    {children}
                </div>
                   
                </PopoverTrigger>
            <PopoverContent className="overflow-scroll max-h-100 top-500 w-100" align="start">
            
                <Input
                    id="search-player"
                    type="text"
                    placeholder="search employee ...."
                    onChange={(e) => { setQuery(e.target.value) }}
                />
                {
                    data?.map((item, i) => <div key={`item-${i}`} onClick={() => {
                        setOpen(false)
                        onSelectItem(item)
                    }}>{render(item)}</div>)
                }
            </PopoverContent>
        </Popover>
    )
}

export default Searchable