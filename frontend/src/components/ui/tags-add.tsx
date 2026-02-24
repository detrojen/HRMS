import { useRef } from "react"
import { Input } from "./input"
import type { ControllerRenderProps, UseFormReturn } from "react-hook-form"
import { Badge } from "./badge"

const TagsAdd = ({ field, form, placeholder }: {
    field: ControllerRenderProps<any, string>
    form: UseFormReturn<any, any, any>
    placeholder?: string
}) => {
    const skillRef = useRef<HTMLInputElement>(null)
    const tags = field.value??[]
    return (
        <div>
            <Input
                ref={skillRef}
                type="text"
                onKeyDown={(e) => {
                   
                    if (e.key === "Enter" && skillRef.current != null) {
                        
                        form.setValue(field.name, [ ...tags,skillRef.current?.value ])
                        skillRef.current.value = ""
                    }

                }}
                placeholder={placeholder}></Input>
            <div className="flex gap-2 my-2">
                {
                  field.value &&  field.value.map(
                        item => <Badge key={item} className="text-sm px-5" onClick={()=>{
                            console.log(field.value.filter(tag => tag != item))
                            form.setValue(field.name, field.value.filter(tag => tag != item))
                        }}>{item} </Badge>
                    )
                }
            </div>
        </div>
    )
}

export default TagsAdd