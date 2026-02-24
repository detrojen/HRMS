import useShareJobMutation from "@/api/mutations/share-job.mutation"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Share } from "lucide-react"
import { useRef } from "react"

const JobShare = ({jobId}:{jobId:number}) => {
    const inputRef = useRef<HTMLInputElement>(null)
    const shareJobMutation = useShareJobMutation();
    return (
        <>
            <Dialog>
                <DialogTrigger><Share></Share></DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Share this job with your friend Job id : -{jobId}</DialogTitle>
                        <DialogDescription>
                           <Input type="mail" ref={inputRef} onChange={(e)=>{
                            if(inputRef.current != null) {
                                inputRef.current.value = e.target.value
                            }
                           }}/>
                           <Button 
                            onClick={()=>{
                                
                                shareJobMutation.mutate({
                                    jobId:jobId,
                                    data: {email: inputRef.current?.value!}
                                })
                            }}
                            className="mt-1">Share {jobId}</Button>
                        </DialogDescription>
                    </DialogHeader>
                </DialogContent>
            </Dialog>
        </>
    )
}

export default JobShare