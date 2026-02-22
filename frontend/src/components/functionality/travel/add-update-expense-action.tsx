import { Button } from "@/components/ui/button";
import { DatePicker } from "@/components/ui/date-picker";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Field, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select";
import type { TAddUpdateExpense } from "@/types/apiRequestTypes/TAddUpdateExpense.type";
import type { UseMutationResult } from "@tanstack/react-query";
import type { AxiosResponse } from "axios";
import { Icon, PlusCircle, type LucideIcon } from "lucide-react";
import { useEffect, useState } from "react";
import { Controller, useForm, type ControllerRenderProps } from "react-hook-form";

type TAddUpdateExpenseActionProps = {
    title?: string
    icon?: LucideIcon
    travelId: number | string,
    expense?:Pick<TAddUpdateExpense,"expenseDetails"> | undefined
    mutation: () => UseMutationResult<AxiosResponse<any, any, {}>, Error, TAddUpdateExpense & { travelId: number | string }, unknown>
}
const AddUpdateExpenseAction = ({ travelId, mutation, expense ,icon,title}: TAddUpdateExpenseActionProps) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const uploadTravelDocumentMutation = mutation()
    const form = useForm<TAddUpdateExpense>({
        defaultValues: expense?{expenseDetails:{...expense.expenseDetails}}:{}
    })

    useEffect(() => {
        if (uploadTravelDocumentMutation.isSuccess) setIsOpen(false)
    }, [uploadTravelDocumentMutation.isSuccess])

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>, field: ControllerRenderProps<TAddUpdateExpense, "file">) => {
        if (e.target.files != null && e.target.files.length > 0) {
            form.setValue(field.name, e.target.files[0]);
        }
    }
    const handleSubmit = form.handleSubmit((values) => {
        uploadTravelDocumentMutation.mutate({ ...values, travelId })
    })
    return (
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger  className="flex gap-0.5"><PlusCircle/></DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Upload travel documnet</DialogTitle>
                </DialogHeader>
            
                <Controller
                    control={form.control}
                    name="expenseDetails.dateOfExpense"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <DatePicker value={field.value!} title={"Date of expense"} onSelect={(date) => {
                                form.setValue(field.name, date)
                            }} />
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="expenseDetails.askedAmount"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <FieldLabel>Expense Amount</FieldLabel>
                            <Input type="number" {...field} />
                        </Field>
                    )}
                />
                <Controller
                    control={form.control}
                    name="expenseDetails.categoryId"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <FieldLabel>Type</FieldLabel>
                            <Select value={field.value} onValueChange={field.onChange}>
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
                        </Field>
                    )}
                />
                 <Controller
                    control={form.control}
                    name="expenseDetails.description"
                    render={({ field, fieldstate }) => (
                        <Field>
                            <Field>
                            <FieldLabel>Description</FieldLabel>
                            <Input  {...field} />
                        </Field>
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
                <Button className="mt-2" onClick={() => handleSubmit()}>Save</Button>

            </DialogContent>
        </Dialog>
    )

}

export default AddUpdateExpenseAction