import useCommentMutation from "@/api/mutations/comment-mutation"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog"
import { Field, FieldLabel } from "@/components/ui/field"
import { Textarea } from "@/components/ui/textarea"
import type { TComment } from "@/types/apiRequestTypes/TCommentRequest.type"
import { useQueryClient, type UseMutationResult } from "@tanstack/react-query"
import type { AxiosResponse } from "axios"
import { ChartBar, Edit, MessageCircle, type LucideIcon } from "lucide-react"
import { useEffect, useState } from "react"
import { Controller, useForm } from "react-hook-form"
import { useOutletContext } from "react-router-dom"

type TCommentActionProps = {
    postId?:string|number, 
    value?:TComment,
    icon: LucideIcon,
    mutation: ()=>UseMutationResult<AxiosResponse<any, any, {}>, Error, any, unknown>
}
 
const CommentAction = ({postId, value, mutation, icon}:TCommentActionProps) => {
     const [isOpen, setIsOpen] = useState<boolean>(false);
     const {setIsLoading} = useOutletContext();
    const commentMutation = mutation()
    const form = useForm<TComment>({
        defaultValues: value??{comment:""}
    });
    const queryClient = useQueryClient();
    const handleSubmit = form.handleSubmit((values)=>{
        let finalValue = {...values}
        if(postId!=undefined) {
            finalValue= {...finalValue,postId:postId}
        }
        commentMutation.mutate({
            ...values,
        })
    })
    useEffect(()=>{
        if(commentMutation.isSuccess){
            setIsOpen(false)
            queryClient.invalidateQueries({
                queryKey:["posts"]
            })
        }
        setIsLoading(commentMutation.isPending)
    },[commentMutation.isSuccess,commentMutation.isPending])
    return(
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger>
                {
                    value?.id ? <Edit/>:<MessageCircle/>
                }
            </DialogTrigger>
            <DialogContent>
                <Controller 
                    name="comment"
                    control={form.control}
                    render={({ field, })=>(
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

export default CommentAction