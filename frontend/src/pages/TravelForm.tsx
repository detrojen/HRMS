import { useGetchEmployeesByNameLike } from "@/api/queries/employee.queries"
import { createTravel } from "@/api/services/travel.service"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Card } from "@/components/ui/card"
import { DatePicker } from "@/components/ui/date-picker"
import { Field, FieldGroup, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import { Separator } from "@/components/ui/separator"
import { Textarea } from "@/components/ui/textarea"
import type { TCreateTravelRequest } from "@/types/apiRequestTypes/TcreateTravelRequest.type"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"
import { useEffect, useState } from "react"
import { Controller, useForm, type UseFormReturn } from "react-hook-form"

const TravelAddEmployeesField = ({form}:{form: UseFormReturn<TCreateTravelRequest,any,TCreateTravelRequest>})=>{
    const [nameQuery, setNameQuery] = useState("")
    const {data} = useGetchEmployeesByNameLike(nameQuery);
    const [employees, seEmployees] = useState<TEmployeeWithNameOnly[]>([])
    useEffect(()=>{
        form.setValue("employeeIds",employees.map(reviewer=>reviewer.id))
    },[employees])
    return (
        <>

          <FieldLabel >Add Reviewers</FieldLabel>
          <Input
            id="search-player"
            type="text"
            placeholder="search player"
            onChange={(e) => { setNameQuery(e.target.value) }}
          />

        {
          data?.map(e => <h1 onClick={() => { setNameQuery(""); seEmployees(reviewers=>[...reviewers, e]); }}>{e.firstName}</h1>)
        }
        <Separator></Separator>
        {
          employees.map(employee => <div key={`reviewer-${employee.id}`} className="flex gap-5">
            <h1>{employee.firstName} </h1>
            <Button size={"xs"} onClick={() => {
              seEmployees(employees.filter( reviewer=> reviewer.id != employee.id))
            }}>remove</Button>
          </div>
          )
        }
        </>
    )
}

const TravelBasicDetailFields = ({form}:{form: UseFormReturn<TCreateTravelRequest,any,TCreateTravelRequest>}) => {
    return(
        <>
            <Controller 
                control={form.control}
                name="title"
                render={({field,fieldState})=>(
                    <Field>
                        <FieldLabel>Title</FieldLabel>
                        <Input {...field}/>
                    </Field>
                )}
            />
            <Controller 
                control={form.control}
                name="employeeIds"
                render={({field,fieldState})=>(
                    <Field>
                        <TravelAddEmployeesField form={form}/>
                    </Field>
                )}/>
            <Controller 
                control={form.control}
                name="startDate"
                render={({field,fieldState})=>(
                    <Field>
                        <DatePicker title="Start Date" onSelect={(date)=>{form.setValue(field.name,date)}}/>
                    </Field>
                )}
            />
            <Controller 
                control={form.control}
                name="endDate"
                render={({field,fieldState})=>(
                    <Field>
                        <DatePicker title="End Date" onSelect={(date)=>{form.setValue(field.name,date)}}/>
                    </Field>
                )}
            />
            <Controller 
                control={form.control}
                name="lastDateToSubmitExpense"
                render={({field,fieldState})=>(
                    <Field>
                        <DatePicker title="Last Date to submit expense" onSelect={(date)=>{form.setValue(field.name,date)}}/>
                    </Field>
                )}
            />
            
        </>
    )
}

const TravelDescriptionField = ({form}:{form: UseFormReturn<TCreateTravelRequest,any,TCreateTravelRequest>}) => {
    return (
        <Controller 
                control={form.control}
                name="descripton"
                render={({field,fieldState})=>(
                    <Field>
                        <FieldLabel>Description</FieldLabel>
                        <Textarea {...field}/>
                    </Field>
                )}
            />
    )
}

const TravelForm = () => {
    const form = useForm<TCreateTravelRequest>({
        defaultValues:{
            title:"Travel 1",
            descripton:"travel 1",
            employeeIds:[10,11,9],
            maxReimbursementAmountPerDay: 10,
            startDate: new Date("2026-02-11"),
            endDate: new Date("2026-02-19"),
            lastDateToSubmitExpense: new Date("2026-02-29")
        }
    })

    const handleSubmit = form.handleSubmit((values)=>{
        createTravel(values);
    })
    return (
        <>
        <Card className="w-1/1 p-4">
            <FieldGroup>
                <TravelBasicDetailFields form={form}/>
                <TravelDescriptionField form={form} />
                <Button onClick={()=>{handleSubmit()}}>Submit</Button>
            </FieldGroup>
        </Card>
        </>
    )
}

export default TravelForm