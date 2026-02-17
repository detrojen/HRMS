import { Card, CardContent } from "@/components/ui/card"
import TravelDocumentTable from "./travel-document-table"
import AddTravelDocumnetAction from "./add-travel-documnet-action"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import useUploadEmployeeTravelDocumentMutation from "@/api/mutations/upload-employee-travel-document.mutation"

const TravelPersonalDocumnetDetails = () => {
    const { personalDocumnets: documents, id: travelId } = useContext(TravelDetailContext)
    return (
        <Card className="w-1/1">
            <CardContent>
                <div className="w-1/1 flex justify-end ">
                    <AddTravelDocumnetAction travelId={travelId} mutation={useUploadEmployeeTravelDocumentMutation} />
                </div>
                <TravelDocumentTable documents={documents} canUpdate={true} />
            </CardContent>
        </Card>
    )
}

export default TravelPersonalDocumnetDetails