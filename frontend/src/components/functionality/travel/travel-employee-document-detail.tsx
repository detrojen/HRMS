import { Card, CardContent } from "@/components/ui/card"
import TravelDocumentTable from "./travel-document-table"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import useDeleteTravelEmployeeDocumnetMutation from "@/api/mutations/delete-travel-employee-documnet.mutation"

const TravelEmployeeDocumnetDetails = () => {
    const { employeeDocuments: documents } = useContext(TravelDetailContext)
    return (
        <Card className="w-1/1">
            <CardContent>
                <TravelDocumentTable deleteMutation={useDeleteTravelEmployeeDocumnetMutation} documents={documents} canAddOrModify={false} />
            </CardContent>
        </Card>
    )
}

export default TravelEmployeeDocumnetDetails