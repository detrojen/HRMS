import useReviewCvMutation from "@/api/mutations/review-cv.mutation"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Field, FieldError, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import type { TReviewCvRequest } from "@/types/apiRequestTypes/TReviewCvRequest.type"
import cvReviewSchema from "@/validation-schema/cvReviewSchema"
import { zodResolver } from "@hookform/resolvers/zod"
import { useQueryClient } from "@tanstack/react-query"
import { ThumbsUp } from "lucide-react"
import { useEffect, useState } from "react"
import { Controller, useForm } from "react-hook-form"
import { useOutletContext } from "react-router-dom"

const ReviewCvAction = ({ jobApplicationId }: { jobApplicationId: number }) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const { setIsLoading } = useOutletContext()
    const reviewCvMutation = useReviewCvMutation()
    const queryClient = useQueryClient()
    const form = useForm<TReviewCvRequest>({
        defaultValues: { review: "" }
        , resolver: zodResolver(cvReviewSchema)
    })
    useEffect(() => {

        setIsLoading(reviewCvMutation.isPending)
        if (reviewCvMutation.isSuccess) {
            queryClient.invalidateQueries({queryKey:[`job-applications`]})
            setIsOpen(false)
        }
    }, [reviewCvMutation.isPending, reviewCvMutation.isSuccess])
    const handleSubmit = form.handleSubmit((values) => {
        reviewCvMutation.mutate({
            jobApplicationId,
            review: values
        })
    })
    return (

        <>
        <Button onClick={() => { setIsOpen(!isOpen) }}>Review Cv</Button>
            <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
                <DialogTrigger ></DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Review cv</DialogTitle>
                        <DialogDescription>

                            <Controller
                                control={form.control}
                                name="review"
                                render={({ field, fieldState }) => {
                                    return <Field>
                                        <FieldLabel>Review</FieldLabel>
                                        <Input {...field} />
                                        <FieldError>{fieldState.error?.message}</FieldError>
                                    </Field>
                                }}
                            />
                            <Button onClick={handleSubmit}>Submit</Button>
                        </DialogDescription>
                    </DialogHeader>
                </DialogContent>
            </Dialog>
        </>
    )
}

export default ReviewCvAction