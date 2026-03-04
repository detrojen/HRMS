import DocViewer from "@/components/ui/doc-viewer"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type"
import type { TTravelDoucmentResponse } from "@/types/apiResponseTypes/TTravelDocumnetResponse.type"
import type { UseMutationResult } from "@tanstack/react-query"
import type { AxiosResponse } from "axios"
import { Edit, Trash } from "lucide-react"
import AddUpdateTravelDocumnetAction from "./add-update-travel-documnet-action"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import { AuthContext } from "@/contexts/AuthContextProvider"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { TTDeleteTravelDocumnetRequest } from "@/types/apiRequestTypes/TTDeleteTravelDocumnetRequest.type"

type TTravelDocumentTableProps = {
    documents: TTravelDoucmentResponse[],
    canUpdate: boolean,
    deleteMutation: () => UseMutationResult<AxiosResponse<TGlobalResponse<boolean>, any, {}>, Error, TTDeleteTravelDocumnetRequest, unknown>
    updateMutation?: () => UseMutationResult<AxiosResponse<any, any, {}>, Error, TUploadTravelDocumnetRequest, unknown>
}
const TravelDocumentTable = ({ documents, canUpdate, updateMutation, deleteMutation }: TTravelDocumentTableProps) => {
    const { id } = useContext(TravelDetailContext);
    const {user} = useContext(AuthContext)
    const deleteMutationInstance = deleteMutation()
    const handleDelete = (documentId:number) => {
        deleteMutationInstance.mutate({
            documentId,
            travelId:id
        })
    }
    return (
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead>Type</TableHead>
                    <TableHead>Description</TableHead>
                    <TableHead>Uploaded by</TableHead>
                    <TableHead>Action</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {
                    documents.map(document => (
                        <TableRow>
                            <TableCell>{document.type}</TableCell>
                            <TableCell>{document.description}</TableCell>
                            <TableCell>{document.uploadedBy.firstName} {document.uploadedBy.lastName}</TableCell>
                            <TableCell className="flex">
                                <DocViewer url={`/api/resource/travel-documents/${document.documentPath}`} />
                                {canUpdate ? <AddUpdateTravelDocumnetAction Actionicon={Edit} mutation={updateMutation!} travelId={id} defaultValues={{
                                    documentDetails: {
                                        id: document.id, type: document.type, description: document.description, documentPath: document.documentPath
                                    }
                                }} /> : <></>}
                                {user.id == document.uploadedBy.id &&<Trash className="text-red-400" onClick={()=>{handleDelete(document.id)}}/>}
                            </TableCell>
                        </TableRow>
                    ))
                }

            </TableBody>
        </Table>
    )
}

export default TravelDocumentTable