import useDeleteExpenseProofMutation from "@/api/mutations/delete-expense-proof.mutation"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { DocRenderer } from "@/components/ui/doc-viewer"
import type { TExpenseDocument } from "@/types/apiResponseTypes/TExpenseDocument.type"
import { Eye, Trash } from "lucide-react"

type TViewExpenseProofActionProps = {
    proofs: TExpenseDocument[]
    expenseId?: number
}
const ViewExpenseProofAction = ({ proofs, expenseId }: TViewExpenseProofActionProps) => {
    const deleteExpenseProofMutation = useDeleteExpenseProofMutation()
    const handleDelete = (documentId: number) => {
        if (confirm("Are you sure want to delete this proof?")) {
            deleteExpenseProofMutation.mutate({
                expenseId:expenseId!, documentId
            })
        }
    }
    return (
        <Dialog >
            <DialogTrigger><Eye className="text-blue-500" /></DialogTrigger>

            <DialogContent className="h-7/8 overflow-y-scroll w-1/2">
                <DialogHeader>
                    <DialogTitle>Documnet</DialogTitle>
                    <div className="h-1/1 ">
                        {
                            proofs.map(proof => (
                                <div>
                                    {expenseId && <Button onClick={()=>handleDelete(proof.id)}><Trash /></Button>}
                                    <DocRenderer key={proof.id} url={"/api/resource/expenses/" + proof.documentPath} />
                                </div>
                            ))
                        }
                    </div>
                </DialogHeader>
            </DialogContent>
        </Dialog>
    )
}

export default ViewExpenseProofAction