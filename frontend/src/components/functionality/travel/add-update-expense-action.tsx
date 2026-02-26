import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/date-picker";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Field, FieldError, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select";
import type { TAddUpdateExpense } from "@/types/apiRequestTypes/TAddUpdateExpense.type";
import { expenseSchema } from "@/validation-schema/expense-schema";
import { zodResolver } from "@hookform/resolvers/zod";
import type { UseMutationResult } from "@tanstack/react-query";
import type { AxiosResponse } from "axios";
import { format } from "date-fns";
import { Edit, Icon, PlusCircle, type LucideIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { Controller, useForm, type ControllerRenderProps } from "react-hook-form";
import { useOutletContext } from "react-router-dom";

type TAddUpdateExpenseActionProps = {
    title?: string
    icon?: LucideIcon
    travelId: number | string,
    expense?: Pick<TAddUpdateExpense, "expenseDetails"> | undefined
    mutation: () => UseMutationResult<AxiosResponse<any, any, {}>, Error, TAddUpdateExpense & { travelId: number | string }, unknown>
}
const AddUpdateExpenseAction = ({ travelId, mutation, expense, icon, title }: TAddUpdateExpenseActionProps) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const{setIsLoading} = useOutletContext()
    const uplaodExpenseMutation = mutation()
    const form = useForm<TAddUpdateExpense>({
        defaultValues: expense ? { expenseDetails: { ...expense.expenseDetails } } : {
            expenseDetails: {
                description: "",
                categoryId: 0,
                askedAmount: 0,
                dateOfExpense: undefined,
            },
            
        }
        , resolver: zodResolver(expenseSchema)
    })

    useEffect(()=>{
      
        setIsLoading(uplaodExpenseMutation.isPending)
        if(uplaodExpenseMutation.isSuccess){

            setIsOpen(false)
        }
    },[uplaodExpenseMutation.isPending, uplaodExpenseMutation.isSuccess])

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>, field: ControllerRenderProps<TAddUpdateExpense, "file">) => {
        if (e.target.files != null && e.target.files.length > 0) {
            form.setValue(field.name!, e.target.files[0]);
        }
    }
    const handleSubmit = form.handleSubmit((values) => {
        uplaodExpenseMutation.mutate({ ...values, travelId })
    })
    return (

        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger className="flex gap-0.5">{expense != undefined ? <Edit /> : <PlusCircle />}</DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Expense</DialogTitle>
                </DialogHeader>
                
                <Controller
                    control={form.control}
                    name="expenseDetails.dateOfExpense"
                    render={({ field, fieldState: { error } }) => (
                        <Field>
                            <DatePicker value={field.value!} title={"Date of expense"} onSelect={(date) => {
                                form.setValue(field.name, new Date(format(date,"yyyy-MM-dd")))
                            }} />
                            <FieldError>{error?.message}</FieldError>
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="expenseDetails.askedAmount"
                    render={({ field, fieldState }) => (
                        <Field>
                            <FieldLabel>Expense Amount</FieldLabel>
                            <Input type="number" {...field} />
                            <FieldError>{fieldState?.error?.message}</FieldError>
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="expenseDetails.categoryId"
                    render={({ field, fieldState }) => (
                        <Field>
                            <FieldLabel>Type</FieldLabel>
                            <Select value={field.value.toString()} onValueChange={field.onChange}>
                                <SelectTrigger className="w-full max-w-48">
                                    <SelectValue placeholder="Select a category" />
                                </SelectTrigger>
                                <SelectContent>
                                    <SelectGroup>
                                        <SelectLabel>Fruits</SelectLabel>
                                        <SelectItem value="1">Food</SelectItem>
                                        <SelectItem value="2">Transport</SelectItem>
                                        <SelectItem value="3">Stay</SelectItem>
                                    </SelectGroup>
                                </SelectContent>
                            </Select>
                            <FieldError>{fieldState?.error?.message}</FieldError>
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="expenseDetails.description"
                    render={({ field, fieldState }) => (

                        <Field>
                            <FieldLabel>Description</FieldLabel>
                            <Input  {...field} />
                            <FieldError>{fieldState?.error?.message}</FieldError>
                        </Field>

                    )}
                />
                <Controller
                    control={form.control}
                    name="file"
                    render={({ field, fieldState }) => (
                        <Field>
                            <FieldLabel>File</FieldLabel>
                            <Input type="file" onChange={(e) => {
                                handleFileChange(e, field)
                            }} />
                            <FieldError>{fieldState?.error?.message}</FieldError>
                        </Field>
                    )}
                />
                <Button className="mt-2" onClick={() => handleSubmit()}>Save</Button>

            </DialogContent>
        </Dialog>
    )

}

export default AddUpdateExpenseAction

// Resolver<{ expenseDetails: { description: string; categoryId: unknown; askedAmount: unknown; dateOfExpense: unknown; id?: unknown; reciept?: string | undefined; }; file?: File | undefined; }, any, { expenseDetails: { description: string; ... 4 more ...; reciept?: string | undefined; }; file?: File | undefined; }>' is not assignable to type 'Resolver<{ expenseDetails: { description: string; categoryId: number; askedAmount: number; dateOfExpense: Date; id?: number | undefined; reciept?: string | undefined; }; file?: File | undefined; }, any, { ...; }>'.
// ResolverOptions<{ expenseDetails: { description: string; categoryId: number; askedAmount: number; dateOfExpense: Date; id?: number | undefined; reciept?: string | undefined; }; file?: File | undefined; }>' is not assignable to type 'ResolverOptions<{ expenseDetails: { description: string; categoryId: unknown; askedAmount: unknown; dateOfExpense: unknown; id?: unknown; reciept?: string | undefined; }; file?: File | undefined; }>'.