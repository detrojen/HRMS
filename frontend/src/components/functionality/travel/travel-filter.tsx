import { Button } from "@/components/ui/button"
import { DatePicker } from "@/components/ui/date-picker"
import { Field, FieldGroup, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import MultiSelectDropDown from "@/components/ui/multi-select-dropdown"
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select"
import { AuthContext } from "@/contexts/AuthContextProvider"
import useDebounce from "@/hooks/use-debounce"
import { format } from "date-fns"
import React, { useContext, useState } from "react"
import { useSearchParams } from "react-router-dom"

const TravelFilter = React.memo(() => {
    const {user} = useContext(AuthContext)
    const [searchParams,setSearchParams] = useSearchParams()
    return (
       <div>
         <FieldGroup className="flex flex-row gap-2 mb-2">
            <GenralFilter />
            {user.role === "HR" && <HrFilter />}
        </FieldGroup>
        <Button onClick={()=>{setSearchParams({})}}>Clear filter</Button>
       </div>
        
    )
})

const HrFilter = () => {
    const [employee, setEmployee] = useState<TEmployeeMinDetail[]>([])
    const [searchParams, setSearchParams] = useSearchParams()
    return (
        <>
            <Field>
                <FieldLabel>Select Employee</FieldLabel>
                <MultiSelectDropDown
                    defaultValue={searchParams.getAll("employees").map(i=>Number(i))}
                    placeHolder="..."
                    data={[1, 2, 3]}
                    showInputValue={(data) => data.toString()}
                    onSelectChange={(checked, data) => {
                        if (checked) {
                            searchParams.append("employees", data.toString())
                        } else {
                            searchParams.delete("employees", data.toString())
                        }
                        setSearchParams(searchParams)
                    }}
                    render={(data) => {
                        return <h1>{data.toString()}</h1>
                    }} >

                </MultiSelectDropDown>
            </Field>
        </>
    )
}

const GenralFilter = () => {
    const [searchParams, setSearchParams] = useSearchParams()
    const handleSearchParams = (key: string, value: string) => {
        searchParams.set(key, value)
        setSearchParams(searchParams)
    }
    const [title, _, setTitle] = useDebounce<string>(searchParams.get("title")??"", 1000, (data) => {
        handleSearchParams('title', data)
    })
    return (
        <>
            <Field>
                <FieldLabel>Title</FieldLabel>
                <Input value={title} onChange={(e) => { setTitle(e.currentTarget.value) }} />
            </Field>
            <Field>
                <FieldLabel >Status</FieldLabel>
                <Select defaultValue={searchParams.get("status")??""} onValueChange={(value) => { handleSearchParams("status", value) }}>
                    <SelectTrigger aria-multiselectable><SelectValue /></SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectItem value="Initiated">Initiated</SelectItem>
                            <SelectItem value="Started">Started</SelectItem>
                            <SelectItem value="Ended">Ended</SelectItem>
                            <SelectItem value="Cancelled">Cancelled</SelectItem>
                        </SelectGroup>
                    </SelectContent>

                </Select>
            </Field>
            <Field>
                <DatePicker value={searchParams.get("startDate") === null ? null : new Date(searchParams.get("startDate")!)} title="Start Date" onSelect={(date) => { handleSearchParams("startDate", format(date, "yyyy-MM-dd")) }} />
            </Field>
            <Field>
                <DatePicker value={searchParams.get("endDate") === null ? null : new Date(searchParams.get("endDate")!)} title="End Date" onSelect={(date) => { handleSearchParams("endDate", format(date, "yyyy-MM-dd")) }} />
            </Field>

        </>
    )
}

export default TravelFilter