import useReviewExpenseMutation from "@/api/mutations/review-expense.mutation";
import useUploadTravelDocumentMutation from "@/api/mutations/upload-travel-document.mutation";
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import DocViewer from "@/components/ui/doc-viewer";
import { Field, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import type { TReviewExpenseRequest } from "@/types/apiRequestTypes/TReviewExpenesRequest.type";
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type";
import type { UseMutationResult } from "@tanstack/react-query";
import type { AxiosResponse } from "axios";
import { useEffect, useState } from "react";
import { Controller, useForm, type ControllerRenderProps } from "react-hook-form";

type TReviewExpenseActionProps = {
    reciept: string,
    id: number,
    askedAmount : number,
    remark: string,
    aprrovedAmount: number
}
const ReviewExpenseAction = (props:TReviewExpenseActionProps) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const reivewExpenseMutation = useReviewExpenseMutation()
    const form = useForm<TReviewExpenseRequest>({
        defaultValues: {
            ...props
        }
    })

    useEffect(()=>{
        setIsOpen(false)
    },[reivewExpenseMutation.isSuccess])


    const handleSubmit = form.handleSubmit((values)=>{
        reivewExpenseMutation.mutate(values)
    })
    return (
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger>Review Expense</DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Upload travel documnet</DialogTitle>
                </DialogHeader>
                <p>Asked amount:- {props.askedAmount}</p>
                <Controller
                    control={form.control}
                    name="aprrovedAmount"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <FieldLabel>Approv </FieldLabel>
                            <Input type="number" {...field} />
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="remark"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <FieldLabel>Type</FieldLabel>
                            <Textarea {...field} />
                        </Field>
                    )}
                />
                <DocViewer url={`/api/resource/expenses/${props.reciept}`} />
                <Button className="mt-2" onClick={()=>handleSubmit()}>Save</Button>

            </DialogContent>
        </Dialog>
    )

}

export default ReviewExpenseAction