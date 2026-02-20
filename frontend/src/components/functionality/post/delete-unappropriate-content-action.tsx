import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog"
import { Field, FieldLabel } from "@/components/ui/field"
import { Textarea } from "@/components/ui/textarea"
import type { TDeleteUnappropriateContent } from "@/types/apiRequestTypes/TDeleteUnappropriateContent.type"
import { useQueryClient, type UseMutationResult } from "@tanstack/react-query"
import type { AxiosResponse } from "axios"
import { Trash2 } from "lucide-react"
import { useEffect, useState } from "react"
import { Controller, useForm } from "react-hook-form"

const DeleteUnappropriateContentAction = ({ contentId, deleteMutation }: {
    contentId: number,
    deleteMutation: () => UseMutationResult<AxiosResponse<any, any, {}>, Error, TDeleteUnappropriateContent , unknown>
}) => {

    const [isOpen, setIsOpen] = useState<boolean>(false);
    const deleterMutationInstance = deleteMutation()
    const form = useForm<TDeleteUnappropriateContent>({
        defaultValues: {
            id: contentId
        }
    });
    const queryClient = useQueryClient();
    const handleSubmit = form.handleSubmit((values) => {
        deleterMutationInstance.mutate(values)
    })
    useEffect(() => {
        if (deleterMutationInstance.isSuccess) {
            setIsOpen(false)
            queryClient.invalidateQueries({
                queryKey: ["posts"]
            })
        }
    }, [deleterMutationInstance.isSuccess])
    return (
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger>
                <Trash2 className="text-amber-500" />
            </DialogTrigger>
            <DialogContent>
                <Controller
                    name="remark"
                    control={form.control}
                    render={({ field, }) => (
                        <Field>
                            <FieldLabel>
                                Comment
                            </FieldLabel>
                            <Textarea {...field} />
                        </Field>
                    )}
                />
                <Button onClick={handleSubmit}>Submit</Button>
            </DialogContent>
        </Dialog>
    )
}

export default DeleteUnappropriateContentAction