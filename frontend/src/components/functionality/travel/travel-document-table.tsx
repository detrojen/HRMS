import { Button } from "@/components/ui/button"
import DocViewer from "@/components/ui/doc-viewer"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import type { TUploadTravelDocumnetRequest } from "@/types/apiRequestTypes/TUploadTravelDocumentRequest.type"
import type { TTravelDoucmentResponse } from "@/types/apiResponseTypes/TTravelDocumnetResponse.type"
import type { UseMutationResult } from "@tanstack/react-query"
import type { AxiosResponse } from "axios"
import { Edit } from "lucide-react"
import AddUpdateTravelDocumnetAction from "./add-update-travel-documnet-action"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"

const TravelDocumentTable = ({ documents, canUpdate, updateMutation }: { documents: TTravelDoucmentResponse[], canUpdate: boolean, updateMutation?:()=> UseMutationResult<AxiosResponse<any, any, {}>, Error, TUploadTravelDocumnetRequest, unknown>}) => {
    const {id} = useContext(TravelDetailContext);
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
                                <DocViewer url={`/api/resource/travel-documents/${document.documentPath}`}/>
                                {canUpdate ? <AddUpdateTravelDocumnetAction Actionicon={Edit} mutation={updateMutation!} travelId={id} defaultValues={{documentDetails:{
                                    id:document.id,type:document.type,description:document.description,documentPath:document.documentPath
                                }}}/>: <></>}
                            </TableCell>
                        </TableRow>
                    ))
                }

            </TableBody>
        </Table>
    )
}

export default TravelDocumentTable