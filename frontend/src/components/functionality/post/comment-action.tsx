import useCommentMutation from "@/api/mutations/comment-mutation"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog"
import { Field, FieldLabel } from "@/components/ui/field"
import { Textarea } from "@/components/ui/textarea"
import type { TComment } from "@/types/apiRequestTypes/TCommentRequest.type"
import { useQueryClient } from "@tanstack/react-query"
import { ChartBar } from "lucide-react"
import { useEffect, useState } from "react"
import { Controller, useForm } from "react-hook-form"

const CommentAction = ({postId}:{postId:string|number}) => {
     const [isOpen, setIsOpen] = useState<boolean>(false);
    const commentMutation = useCommentMutation()
    const form = useForm<TComment>();
    const queryClient = useQueryClient();
    const handleSubmit = form.handleSubmit((values)=>{
        commentMutation.mutate({
            ...values,
            postId
        })
    })
    useEffect(()=>{
        if(commentMutation.isSuccess){
            setIsOpen(false)
            queryClient.invalidateQueries({
                queryKey:["posts"]
            })
        }
    },[commentMutation.isSuccess])
    return(
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger>
                <ChartBar />
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