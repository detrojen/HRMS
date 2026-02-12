import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Share } from "lucide-react"

const JobShareBtn = () => {
    return (
        <>
            <Dialog>
                <DialogTrigger><Share></Share></DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Share this job with your friend</DialogTitle>
                        <DialogDescription>
                           <Input type="mail"/>
                           <Button className="mt-1">Share</Button>
                        </DialogDescription>
                    </DialogHeader>
                </DialogContent>
            </Dialog>
        </>
    )
}

export default JobShareBtn