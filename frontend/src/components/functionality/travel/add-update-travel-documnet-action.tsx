import useUploadTravelDocumentMutation from "@/api/mutations/upload-travel-document.mutation";
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Field, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type";
import type { UseMutationResult } from "@tanstack/react-query";
import type { AxiosResponse } from "axios";
import type { LucideIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { Controller, useForm, type ControllerRenderProps } from "react-hook-form";

const AddUpdateTravelDocumnetAction = ({ travelId, mutation, defaultValues, Actionicon }: {defaultValues?:TUploadTravelDocumnetRequest, travelId: number, mutation:()=> UseMutationResult<AxiosResponse<any, any, {}>, Error, TUploadTravelDocumnetRequest, unknown>, Actionicon:LucideIcon }) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const uploadTravelDocumentMutation = mutation()
    const form = useForm<Omit<TUploadTravelDocumnetRequest,"travelId">>({
        defaultValues: {
            documentDetails: {
                description: defaultValues?.documentDetails.description,
                type:defaultValues?.documentDetails.type,
                documentPath:defaultValues?.documentDetails.documentPath,
                id:defaultValues?.documentDetails.id
            },
           
        }
    })

    useEffect(()=>{
        setIsOpen(false)
    },[uploadTravelDocumentMutation.isSuccess])
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>, field: ControllerRenderProps<TUploadTravelDocumnetRequest, "file">) => {
        if (e.target.files != null && e.target.files.length > 0) {
            form.setValue(field.name, e.target.files[0]);
        }
    }

    const handleSubmit = form.handleSubmit((values)=>{
        debugger
        uploadTravelDocumentMutation.mutate({...values,travelId})
    })
    return (
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger><Actionicon/></DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Upload travel documnet</DialogTitle>
                </DialogHeader>
                <Controller
                    control={form.control}
                    name="documentDetails.description"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <FieldLabel>Description</FieldLabel>
                            <Input {...field} />
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="documentDetails.type"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <FieldLabel>Type</FieldLabel>
                            <Input {...field} />
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="file"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <FieldLabel>File</FieldLabel>
                            <Input type="file" onChange={(e) => {
                                handleFileChange(e, field)
                            }} />
                        </Field>
                    )}
                />
                <Button className="mt-2" onClick={()=>handleSubmit()}>Save</Button>

            </DialogContent>
        </Dialog>
    )

}

export default AddUpdateTravelDocumnetAction