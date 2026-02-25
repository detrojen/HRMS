
import useReviewJobApplication from "@/api/mutations/review-job-application.mutation"
import { Button } from "@/components/ui/button"
import  { DialogTrigger, DialogContent, DialogHeader, DialogTitle, DialogDescription, Dialog } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Check, Cross } from "lucide-react"
import { useEffect, useRef, useState } from "react"
import { useOutletContext } from "react-router-dom"
type TReviewJobApplicationActionProps = {
    status: string
    jobApplicationId:number
}
const ReviewJobApplicationAction = ({status,jobApplicationId}:TReviewJobApplicationActionProps) => {
    const inputRef = useRef<HTMLInputElement>(null)
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const{setIsLoading} = useOutletContext()
    const reviewJobApplicationMutation = useReviewJobApplication()
    useEffect(()=>{
            
            setIsLoading(reviewJobApplicationMutation.isPending)
            if(reviewJobApplicationMutation.isSuccess){
    
                setIsOpen(false)
            }
        },[reviewJobApplicationMutation.isPending, reviewJobApplicationMutation.isSuccess])
    
    return (
        <>
            <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
                <DialogTrigger>
                    {status==="approve"?
                     <Check className="text-green-500"/>:
                     <Cross className="rotate-45 text-red-500"/>}
                </DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Review</DialogTitle>
                        <DialogDescription>
                           <Input type="mail" ref={inputRef} onChange={(e)=>{
                            if(inputRef.current != null) {
                                inputRef.current.value = e.target.value
                            }
                           }}/>
                           <Button 
                            onClick={()=>{
                                reviewJobApplicationMutation.mutate(
                                    {
                                        review: {
                                            status: status,
                                            remark:  inputRef?.current?.value
                                        },
                                        jobApplicationId
                                    }
                                )
                            }}
                            className={`mt-1 ${status==="approve"? "bg-green-500":"bg-red-500"}`}>{status.toUpperCase()}</Button>
                        </DialogDescription>
                    </DialogHeader>
                </DialogContent>
            </Dialog>
        </>
    )
}

export default ReviewJobApplicationAction