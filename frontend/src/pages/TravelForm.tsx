import useCreateTravelMutation from "@/api/mutations/create-travel.mutation"
import useUpdateTravelMutation from "@/api/mutations/update-travel.mutation"
import { useGetchEmployeesByNameLike } from "@/api/queries/employee.queries"
import { useFetchTravelMinDetailsById } from "@/api/queries/travel.queries"
import { createTravel } from "@/api/services/travel.service"
import { Button } from "@/components/ui/button"
import { Card } from "@/components/ui/card"
import { DatePicker } from "@/components/ui/date-picker"
import { Field, FieldError, FieldGroup, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import { Separator } from "@/components/ui/separator"
import { Textarea } from "@/components/ui/textarea"
import type { TCreateTravelRequest } from "@/types/apiRequestTypes/TcreateTravelRequest.type"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"
import { travelSchema } from "@/validation-schema/travel-schema"
import { zodResolver } from "@hookform/resolvers/zod"
import { useEffect, useState } from "react"
import { Controller, useForm, type UseFormReturn } from "react-hook-form"
import { useOutletContext, useParams } from "react-router-dom"

const TravelAddEmployeesField = ({ form }: { form: UseFormReturn<TCreateTravelRequest, any, TCreateTravelRequest> }) => {
    const [nameQuery, setNameQuery] = useState("")
    const { data } = useGetchEmployeesByNameLike(nameQuery);
    const [employees, seEmployees] = useState<TEmployeeWithNameOnly[]>([])
    useEffect(() => {
        form.setValue("employeeIds", employees.map(employee => employee.id))
    }, [employees])
    return (
        <>

            <FieldLabel >Add Employees</FieldLabel>
            <Input
                id="search-player"
                type="text"
                placeholder="search employee"
                onChange={(e) => { setNameQuery(e.target.value) }}
            />

            {
                data?.map(e => <h1 onClick={() => { setNameQuery(""); seEmployees(employees => [...employees, e]); }}>{e.firstName}</h1>)
            }
            <Separator></Separator>
            {
                employees.map(employee => <div key={`reviewer-${employee.id}`} className="flex gap-5">
                    <h1>{employee.firstName} </h1>
                    <Button size={"xs"} onClick={() => {
                        seEmployees(employees.filter(employee => employee.id != employee.id))
                    }}>remove</Button>
                </div>
                )
            }
            <FieldError>{form.formState.errors.employeeIds?.message}</FieldError>
        </>
    )
}

const TravelBasicDetailFields = ({ form }: { form: UseFormReturn<TCreateTravelRequest, any, TCreateTravelRequest> }) => {
    const watchId = form.watch("id")
    const watchStartDate = form.watch("startDate")
    const watchLastDateToSubmitExpense = form.watch("lastDateToSubmitExpense")
    const watchEndDate = form.watch("endDate")

    return (
        <>
            <Controller
                control={form.control}
                name="title"
                render={({ field, fieldState }) => (
                    <Field>
                        <FieldLabel>Title</FieldLabel>
                        <Input {...field} />
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </Field>
                )}
            />
            <Controller
                control={form.control}
                name="maxReimbursementAmountPerDay"
                render={({ field, fieldState }) => (
                    <Field>
                        <FieldLabel>Max reiembesment per day</FieldLabel>
                        <Input type="number"{...field} />
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </Field>
                )}
            />

            {watchId == undefined && <Controller
                control={form.control}
                name="employeeIds"
                render={({ field, fieldState }) => (
                    <Field className="col-span-2">
                        <TravelAddEmployeesField form={form} />
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </Field>
                )} />}
            <Controller
                control={form.control}
                name="startDate"
                render={({ field, fieldState }) => (
                    <Field>
                        <DatePicker title="Start Date" onSelect={(date) => { form.setValue(field.name, date) }} value={watchStartDate} />
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </Field>
                )}
            />
            <Controller
                control={form.control}
                name="endDate"
                render={({ field, fieldState }) => (
                    <Field>
                        <DatePicker title="End Date" onSelect={(date) => { form.setValue(field.name, date) }} value={watchEndDate} />
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </Field>
                )}
            />
            <Controller
                control={form.control}
                name="lastDateToSubmitExpense"
                render={({ field, fieldState }) => (
                    <Field>
                        <DatePicker title="Last Date to submit expense" onSelect={(date) => { form.setValue(field.name, date) }} value={watchLastDateToSubmitExpense} />
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </Field>
                )}
            />

        </>
    )
}

const TravelDescriptionField = ({ form }: { form: UseFormReturn<TCreateTravelRequest, any, TCreateTravelRequest> }) => {
    return (
        <Controller
            control={form.control}
            name="descripton"
            render={({ field, fieldState }) => (
                <Field className="col-span-2">
                    <FieldLabel>Description</FieldLabel>
                    <Textarea {...field} />
                </Field>
            )}
        />
    )
}

const TravelForm = () => {
    const { travelId } = useParams()
    const { setIsLoading } = useOutletContext()
    const createTravelMutation = useCreateTravelMutation()
    const updateTravelMutation = useUpdateTravelMutation()
    const travelQuery = useFetchTravelMinDetailsById(travelId ?? "0")
    const form = useForm<TCreateTravelRequest>({

        defaultValues: {
            id: undefined,
            title: "",
            descripton: "",
            employeeIds: [],
            maxReimbursementAmountPerDay: 0,
            startDate: undefined,
            endDate: undefined,
            lastDateToSubmitExpense: undefined
        },
        resolver: zodResolver(travelSchema)
    })


    useEffect(() => {

        setIsLoading(createTravelMutation.isPending || updateTravelMutation.isPending)
    }, [createTravelMutation.isPending, updateTravelMutation.isPending])
    useEffect(() => {
        if (travelQuery?.isSuccess && travelQuery.data) {
            form.setValue("id", travelQuery.data.data.id)
            form.setValue("title", travelQuery.data.data.title)
            form.setValue("descripton", travelQuery.data.data.descripton)
            form.setValue("maxReimbursementAmountPerDay", travelQuery.data.data.maxReimbursementAmountPerDay)
            //    form.setValue("startDate", new Date("2026-09-08"))
            form.setValue("startDate", travelQuery.data.data.startDate)
            form.setValue("endDate", travelQuery.data.data.endDate)
            form.setValue("lastDateToSubmitExpense", travelQuery.data.data.lastDateToSubmitExpense)
        }
    }, [travelQuery?.isSuccess])
    const handleSubmit = form.handleSubmit((values) => {
        if (values.id) {
            updateTravelMutation.mutate(values)
        } else {
            createTravelMutation.mutate(values)
        }
    })

    if (travelQuery && travelQuery.isLoading) {
        return "Travel details is loading"
    }
    return (
        <>

            <Card className="w-1/1 p-4">
                <form onSubmit={handleSubmit}>
                    <FieldGroup className="grid grid-cols-2">
                        <TravelBasicDetailFields form={form} />
                        <TravelDescriptionField form={form} />
                    </FieldGroup>
                    <Button type="submit">Submit</Button>
                </form>
            </Card>
        </>
    )
}

export default TravelForm