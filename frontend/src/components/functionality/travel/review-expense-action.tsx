import useReviewExpenseMutation from "@/api/mutations/review-expense.mutation";
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Field, FieldError, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import type { TReviewExpenseRequest } from "@/types/apiRequestTypes/TReviewExpenesRequest.type";
import type { TLayoutContext } from "@/types/TlayoutContext.type";
import { FileSearch } from "lucide-react";
import { useEffect, useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { useOutletContext } from "react-router-dom";
import ViewExpenseProofAction from "./view-expense-proof-action";
import type { TExpenseDocument } from "@/types/apiResponseTypes/TExpenseDocument.type";

type TReviewExpenseActionProps = {
    proofs:TExpenseDocument[]
    id: number,
    askedAmount : number,
    remark: string,
    aprrovedAmount: number
}
const ReviewExpenseAction = (props:TReviewExpenseActionProps) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const {setIsLoading} = useOutletContext<TLayoutContext>()
    const reivewExpenseMutation = useReviewExpenseMutation()
    const form = useForm<TReviewExpenseRequest>({
        defaultValues: {
            ...props
        }
    })

    useEffect(()=>{
        setIsLoading(reivewExpenseMutation.isPending)
        if(reivewExpenseMutation.isSuccess){
            setIsOpen(false)
        }
    },[reivewExpenseMutation.isPending, reivewExpenseMutation.isSuccess])


    const handleSubmit = form.handleSubmit((values)=>{
        reivewExpenseMutation.mutate(values)
    })
    return (
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger> <FileSearch/> </DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Review expense</DialogTitle>
                </DialogHeader>
                <p>Asked amount:- {props.askedAmount}</p>
                <Controller
                    control={form.control}
                    name="aprrovedAmount"
                    render={({ field, fieldState }) => (
                        <Field>
                            <FieldLabel>Approv </FieldLabel>
                            <Input type="number" {...field} />
                            <FieldError>{fieldState.error?.message}</FieldError>
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="remark"
                    render={({ field, fieldState }) => (
                        <Field>
                            <FieldLabel>Type</FieldLabel>
                            <Textarea {...field} />
                            <FieldError>{fieldState.error?.message}</FieldError>
                        </Field>
                    )}
                />
                <ViewExpenseProofAction proofs={props.proofs} />
                <Button className="mt-2" onClick={()=>handleSubmit()}>Save</Button>

            </DialogContent>
        </Dialog>
    )

}

export default ReviewExpenseAction