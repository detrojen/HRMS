import { Button } from "@/components/ui/button"
import DocViewer from "@/components/ui/doc-viewer"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import type { TTravelDoucmentResponse } from "@/types/apiResponseTypes/TTravelDocumnetResponse.type"
import { Edit } from "lucide-react"

const TravelDocumentTable = ({ documents, canUpdate }: { documents: TTravelDoucmentResponse[], canUpdate: boolean }) => {
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
                                {canUpdate ? <Edit/> : <></>}
                            </TableCell>
                        </TableRow>
                    ))
                }

            </TableBody>
        </Table>
    )
}

export default TravelDocumentTable