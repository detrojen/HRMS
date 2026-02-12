import useCreateJobMutation from "@/api/mutations/create-job-mutation";
import { Card } from "@/components/ui/card";
import { FieldGroup, FieldLabel } from "@/components/ui/field";
import { Form, FormControl, FormField, FormItem } from "@/components/ui/form"
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import TagsAdd from "@/components/ui/tags-add";
import { Textarea } from "@/components/ui/textarea";
import type { TCreateJobRequest } from "@/types/apiRequestTypes/TCreateJobRequest.type";
import { useEffect, useRef } from "react";
import { useForm, type ControllerRenderProps, type UseFormReturn } from "react-hook-form";

const JobBasicDetailForm = ({ form }: { form: UseFormReturn<TCreateJobRequest, any, TCreateJobRequest> }) => {
    return (
        <>
            <FieldGroup className="w-full">
                <FormField
                    name="jobDetail.title"
                    control={form.control}
                    render={({ field }) => <FormItem>
                        <FieldLabel>Title</FieldLabel>
                        <FormControl>
                            <Input {...field} placeholder="title"></Input>
                        </FormControl>
                    </FormItem>}
                />

                <FormField
                    name="jobDetail.vacancy"
                    control={form.control}
                    render={({ field }) => <FormItem>
                        <FieldLabel>Vacancy</FieldLabel>
                        <FormControl>
                            <Input type="number" {...field} placeholder="vacancy"></Input>
                        </FormControl>
                    </FormItem>}
                />
                <div className="grid grid-cols-3">
                    <FormField
                        name="jobDetail.hrOwnerId"
                        control={form.control}
                        render={({ field }) => <FormItem>
                            <FieldLabel>Select Hr Owner</FieldLabel>
                            <Select name={field.name}
                                value={field.value?.toString()}
                                onValueChange={field.onChange}>
                                <SelectTrigger ><SelectValue placeholder="Select" /></SelectTrigger>
                                <SelectContent>
                                    <SelectGroup>
                                        <SelectItem value="10">Adele Boyer</SelectItem>
                                        <SelectItem value="11"> Ruby Watsica</SelectItem>
                                    </SelectGroup>
                                </SelectContent>
                            </Select>


                        </FormItem>}
                    />
                    <FormField
                        name="jobDetail.workMode"
                        control={form.control}
                        render={({ field }) => <FormItem className="w-1/1">
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
                        </FormItem>}
                    />

                </div>
                <FormField
                    name="jobDetail.skills"
                    control={form.control}
                    render={({ field }) => <FormItem className="w-1/1">
                        <FieldLabel>Skills</FieldLabel>
                        <FormControl>
                            <TagsAdd form={form} field={field} placeholder={"enter skill"} />
                        </FormControl>
                    </FormItem>}
                />
                <FormField
                    name="jobDetail.reviewerIds"
                    control={form.control}
                    render={({ field }) => <FormItem className="w-1/1">
                        <FieldLabel>Reviewer</FieldLabel>
                        <FormControl>
                            <TagsAdd form={form} field={field} placeholder={"enter skill"} />
                        </FormControl>
                    </FormItem>}
                />

            </FieldGroup>
        </>
    )
}

const JobDescriptionForm = ({ form }: { form: UseFormReturn<TCreateJobRequest, any, TCreateJobRequest> }) => {
    return (
        <FieldGroup className="w-full">
            <FormField
                name="jobDetail.description"
                control={form.control}
                render={({ field }) => <FormItem>
                    <FieldLabel>Title</FieldLabel>
                    <FormControl>
                        <Textarea {...field} placeholder="title"></Textarea>
                    </FormControl>
                </FormItem>}
            />
        </FieldGroup>
    )
}

const JobDocumentUploadForm = ({ form }: { form: UseFormReturn<TCreateJobRequest, any, TCreateJobRequest> }) => {
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>, field: ControllerRenderProps<TCreateJobRequest, "jdDocument">) => {
        if (e.target.files != null && e.target.files.length > 0) {
            form.setValue(field.name, e.target.files[0]);
        }
    }
    return (
        <FieldGroup className="w-full">
            <FormField
                name="jdDocument"
                control={form.control}
                render={({ field }) => <FormItem>
                    <FieldLabel>Title</FieldLabel>
                    <FormControl>
                        <Input type="file" onChange={(e) => {
                            handleFileChange(e, field)
                        }} placeholder="description"></Input>
                    </FormControl>
                </FormItem>}
            />
        </FieldGroup>
    )
}


const JobForm = () => {
    const form = useForm<TCreateJobRequest>({ defaultValues: { jobDetail: { hrOwnerId: 11, skills: [], reviewerIds: [] } } });
    const createJobMutation = useCreateJobMutation()
    const onSubmit = (values) => {
            console.log("submiting form")
            createJobMutation.mutate(values)
        }
    
    return (
        <Card className="w-1/1 max-h-full p-2 grid grid-cols-5 gap-2">
            <Form {...form}>
                <div className="col-span-1"></div>
                <div className="col-span-3" onSubmit={form.handleSubmit(onSubmit)}>
                    <JobBasicDetailForm form={form} />
                    <JobDescriptionForm form={form} />
                    <JobDocumentUploadForm form={form} />
                    <button onClick={form.handleSubmit(onSubmit)} >Submit</button>
                </div>
            </Form>
        </Card>
    )
}

export default JobForm