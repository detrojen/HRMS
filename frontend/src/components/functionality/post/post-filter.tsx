import { DatePicker } from "@/components/ui/date-picker"
import { Field, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import { useRef } from "react"
import { useSearchParams } from "react-router-dom"

const PostFilter = () => {
    const [serachParams, setSearchparams] = useSearchParams()
    const debouceRef = useRef<number | null>(null)

    const handleFilterChange = (key: string, value: string) => {
        if (value === "all") {
            serachParams.delete(key)
        } else {
            serachParams.set(key, value)
        }
         if(debouceRef != null){
            clearTimeout(debouceRef.current!)
        }
        debouceRef.current = setTimeout(()=>{
            setSearchparams(serachParams)
        },500)
        
    }
    return (
        <div className="flex gap-1 p-1">
            <Field>
                <FieldLabel>
                    Text
                </FieldLabel>
                <Input onChange={(e) => { handleFilterChange("query", e.target.value) }} />
            </Field>
            <Field>
                <DatePicker title={"post from"} onSelect={(value) => { handleFilterChange("postFrom", `${value.toDateString()}`) }} value={serachParams.get("postFrom") ? new Date(serachParams.get("postFrom")!) : undefined} />
            </Field>
            <Field>
                <DatePicker title={"post to"} onSelect={(value) => { handleFilterChange("postTo", `${value.toDateString()}`) }} value={serachParams.get("postTo") ? new Date(serachParams.get("postTo")!) : undefined} />
            </Field>
        </div>
    )
}
export default PostFilter