import { useRef } from "react"
import { Input } from "./input"
import type { ControllerRenderProps, UseFormReturn } from "react-hook-form"
import { Badge } from "./badge"

const TagsAdd = ({ field, form, placeholder }: {
    field: ControllerRenderProps<any, string>
    form: UseFormReturn<any, any, any>
    placeholder: string
}) => {
    const skillRef = useRef<HTMLInputElement>(null)

    return (
        <div>
            <Input
                ref={skillRef}
                type="text"
                onKeyUp={(e) => {
                   
                    if (e.key === "Enter" && skillRef.current != null) {
                         console.log("adding tags")
                        form.setValue(field.name, [...field.value, skillRef.current?.value])
                        skillRef.current.value = ""
                        console.log(field.value)
                    }

                }}
                placeholder={placeholder}></Input>
            <div className="flex gap-2 my-2">
                {
                    field.value.map(
                        item => <Badge className="text-sm px-5" onClick={()=>{
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