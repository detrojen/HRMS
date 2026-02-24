import useCreateJobMutation from "@/api/mutations/create-job-mutation";
import { useFetchHrList, useGetchEmployeesByNameLike } from "@/api/queries/employee.queries";
import { getEmployeesByNameQuery } from "@/api/services/employee.service";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Field, FieldError, FieldGroup, FieldLabel } from "@/components/ui/field";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form"
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Separator } from "@/components/ui/separator";
import TagsAdd from "@/components/ui/tags-add";
import { Textarea } from "@/components/ui/textarea";
import type { TCreateJobRequest } from "@/types/apiRequestTypes/TCreateJobRequest.type";
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type";
import { useEffect, useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, type ControllerRenderProps, type UseFormReturn } from "react-hook-form";
import { jobCreateSchema } from "@/validation-schema/job-schema";

const JobBasicDetailForm = ({ form }: { form: UseFormReturn<TCreateJobRequest, any, TCreateJobRequest> }) => {
    const { data: hrList } = useFetchHrList()
    return (
        <>
            <Form {...form}>
                <FieldGroup className="w-full">
                    <FormField
                        name="jobDetail.title"
                        control={form.control}
                        render={({ field, fieldState }) => <FormItem>
                            <FieldLabel>Title</FieldLabel>
                            <FormControl >
                                <Input {...field} placeholder="title"></Input>
                            </FormControl>
                            <FieldError>{fieldState.error?.message}</FieldError>
                        </FormItem>}
                    />
                    <FormField
                        name="jobDetail.vacancy"
                        control={form.control}
                        render={({ field, fieldState }) => <FormItem>
                            <FieldLabel>Vacancy</FieldLabel>
                            <FormControl>
                                <Input type="number" {...field} placeholder="vacancy"></Input>
                            </FormControl>
                            <FieldError>{fieldState.error?.message}</FieldError>
                        </FormItem>}
                    />
                    <div className="grid grid-cols-3">
                        <FormField
                            name="jobDetail.hrOwnerId"
                            control={form.control}
                            render={({ field, fieldState }) => <FormItem>
                                <FieldLabel>Select Hr Owner</FieldLabel>
                                <Select name={field.name}
                                    value={field.value?.toString()}
                                    onValueChange={field.onChange}>
                                    <SelectTrigger ><SelectValue placeholder="Select" /></SelectTrigger>
                                    <SelectContent>
                                        <SelectGroup>
                                            {hrList && hrList.data.map(hr => (<SelectItem key={`hr-${hr.id}`} value={hr.id.toString()}>{hr.firstName + " " + hr.lastName}</SelectItem>))}

                                        </SelectGroup>
                                    </SelectContent>
                                </Select>

                                <FieldError>{fieldState.error?.message}</FieldError>

                            </FormItem>}
                        />
                        <FormField
                            name="jobDetail.workMode"
                            control={form.control}
                            render={({ field, fieldState }) => <FormItem className="w-1/1">
                                <FieldLabel>Select work mode</FieldLabel>
                                <Select
                                    name={field.name}
                                    value={field.value?.toString()}
                                    onValueChange={field.onChange}>
                                    <SelectTrigger ><SelectValue placeholder="Select" /></SelectTrigger>
                                    <SelectContent>
                                        <SelectGroup>
                                            <SelectItem value="work from office">Work from office</SelectItem>
                                            <SelectItem value="work from home"> Work from home</SelectItem>
                                            <SelectItem value="hybrid">Hybrid</SelectItem>
                                        </SelectGroup>
                                    </SelectContent>
                                </Select>
                                <FieldError>{fieldState.error?.message}</FieldError>
                            </FormItem>}
                        />

                    </div>
                    <FormField
                        name="jobDetail.skills"
                        control={form.control}
                        render={({ field, fieldState }) => <FormItem className="w-1/1">
                            <FieldLabel>Skills</FieldLabel>
                            <FormControl>
                                <TagsAdd form={form} field={field} placeholder={"enter skill"} />
                            </FormControl>
                            <FieldError>{fieldState.error?.message}</FieldError>
                        </FormItem>}
                    />


                </FieldGroup>
            </Form>
        </>
    )
}

const JobDescriptionForm = ({ form }: { form: UseFormReturn<TCreateJobRequest, any, TCreateJobRequest> }) => {
    return (
        <Form {...form}>
            <FieldGroup className="w-full">
                <FormField
                    name="jobDetail.description"
                    control={form.control}
                    render={({ field, fieldState }) => <FormItem>
                        <FieldLabel>Description</FieldLabel>
                        <FormControl>
                            <Textarea {...field} placeholder="title"></Textarea>
                        </FormControl>
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </FormItem>}
                />
            </FieldGroup>
        </Form>
    )
}

const JobDocumentUploadForm = ({ form }: { form: UseFormReturn<TCreateJobRequest, any, TCreateJobRequest> }) => {
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>, field: ControllerRenderProps<TCreateJobRequest, "jdDocument">) => {
        if (e.target.files != null && e.target.files.length > 0) {
            form.setValue(field.name, e.target.files[0]);
        }
    }
    return (
        <Form {...form}>
            <FieldGroup className="w-full">
                <FormField
                    name="jdDocument"
                    control={form.control}
                    render={({ field, fieldState }) => <FormItem>
                        <FieldLabel>JD</FieldLabel>
                        <FormControl>
                            <Input type="file" onChange={(e) => {
                                handleFileChange(e, field)
                            }} placeholder="description"></Input>
                        </FormControl>
                        <FieldError>{fieldState.error?.message}</FieldError>
                    </FormItem>}
                />
            </FieldGroup>
        </Form>
    )
}

const JobAddCvReviewrersForm = ({ form }: { form: UseFormReturn<TCreateJobRequest, any, TCreateJobRequest> }) => {
    const [nameQuery, setNameQuery] = useState("")
    const { data } = useGetchEmployeesByNameLike(nameQuery);
    const [reviewers, setReviewers] = useState<TEmployeeWithNameOnly[]>([])
    useEffect(() => {
        form.setValue("jobDetail.reviewerIds", reviewers.map(reviewer => reviewer.id))
    }, [reviewers])
    return (
        <>
            <Form {...form}>
                <Field>
                    <FieldLabel >Add Reviewers</FieldLabel>
                    <Input
                        id="search-player"
                        type="text"
                        placeholder="search player"
                        onChange={(e) => { setNameQuery(e.target.value) }}
                    />
                </Field>
                {
                    data?.map(e => <h1 onClick={() => { setNameQuery(""); setReviewers(reviewers => [...reviewers, e]); }}>{e.firstName}</h1>)
                }
                <Separator></Separator>
                {
                    reviewers.map(employee => <div key={`reviewer-${employee.id}`} className="flex gap-5">
                        <h1>{employee.firstName} </h1>
                        <Button size={"xs"} onClick={() => {
                            setReviewers(reviewers.filter(reviewer => reviewer.id != employee.id))
                        }}>remove</Button>
                    </div>
                    )
                }
            </Form>
        </>
    )
}

const JobForm = () => {
    const form = useForm<TCreateJobRequest>(
        {
            defaultValues: {
                jobDetail: {
                    id:null,
                    hrOwnerId: null,
                    skills: [],
                    reviewerIds: [],
                    title: "",
                    vacancy: 0,
                    description: "",
                    workMode: ""
                }, jdDocument: null
            }
            , resolver: zodResolver(jobCreateSchema)
            , mode: "onBlur"
        }

    );
    const createJobMutation = useCreateJobMutation()
    const handleSubmit = form.handleSubmit((values) => {
        console.log(form.formState)
        console.log("submiting form")
        createJobMutation.mutate(values)
    })

    return (
        <Card className="w-1/1 max-h-full p-2 grid grid-cols-5 gap-2">

            <div className="col-span-1"></div>
            <form className="col-span-3" onSubmit={handleSubmit}>
                <JobBasicDetailForm form={form} />
                <JobAddCvReviewrersForm form={form} />
                <JobDescriptionForm form={form} />
                <JobDocumentUploadForm form={form} />
                <button type="submit" >Submit</button>
            </form>
        </Card>
    )
}

export default JobForm