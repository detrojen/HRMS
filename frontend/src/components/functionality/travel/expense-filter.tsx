import { Button } from "@/components/ui/button"
import { DatePicker } from "@/components/ui/date-picker"
import { Field, FieldLabel } from "@/components/ui/field"
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import React, { useEffect } from "react"
import { useContext } from "react"
import { useSearchParams } from "react-router-dom"

const ExpenseFilter = () => {
    const [serachParams, setSearchparams] = useSearchParams()
    const { employees, startDate, endDate } = useContext(TravelDetailContext)

    const handleFilterChange = (key: string, value: string) => {
        if(value === "all"){
            serachParams.delete(key)
        }else{
            serachParams.set(key, value)
        }
        setSearchparams(serachParams)
    }
    const resetFilter = () => {
        setSearchparams({dateFrom:startDate,dateTo:endDate})
    }

    useEffect(()=>{
        ()=>{console.log("ilter form rerendered")}
    },[])
    return (
        <div className="flex gap-1">
            <Field>
                <FieldLabel>Type</FieldLabel>
                <Select defaultValue={serachParams.get("category")?.toString()} onValueChange={(value) => { handleFilterChange("category", value) }}>
                    <SelectTrigger className="w-full max-w-48">
                        <SelectValue placeholder="Select a category" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectLabel>Select expense category</SelectLabel>
                            <SelectItem value="all">All</SelectItem>
                            <SelectItem value="Food">Food</SelectItem>
                            <SelectItem value="Transport">Transport</SelectItem>
                            <SelectItem value="Stay">Stay</SelectItem>
                        </SelectGroup>
                    </SelectContent>
                </Select>
            </Field>
            <Field>
                <FieldLabel>Employee</FieldLabel>
                <Select defaultValue={serachParams.get("employeeId")?.toString()} onValueChange={(value) => { handleFilterChange("employeeId", value) }}>
                    <SelectTrigger className="w-full max-w-48">
                        <SelectValue placeholder="Select a category" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectLabel>Select Employee</SelectLabel>
                            <SelectItem value="all">All</SelectItem>
                            {employees.map(employee => (<SelectItem key={`employee-travel-${employee.id}`} value={employee.id.toString()}>{employee.firstName} {employee.lastName}</SelectItem>))}
                        </SelectGroup>
                    </SelectContent>
                </Select>
            </Field>
            <Field>
                <DatePicker title={"Expense from"} onSelect={(value) => { handleFilterChange("dateFrom", `${value.getFullYear()}-${(value.getMonth()+1).toString().padStart(2,"0")}-${(value.getDate()).toString().padStart(2,"0")}`) }} value={new Date(serachParams.get("dateFrom") ?? startDate)} />
            </Field>
            <Field>
                <DatePicker title={"Expense To"} onSelect={(value) => { handleFilterChange("dateTo", `${value.getFullYear()}-${(value.getMonth()+1).toString().padStart(2,"0")}-${(value.getDate()).toString().padStart(2,"0")}`) }} value={new Date(serachParams.get("dateTo") ?? endDate)} />
            </Field>
            <Button onClick={resetFilter}>Reset</Button>
        </div>
    )
}

export default ExpenseFilter