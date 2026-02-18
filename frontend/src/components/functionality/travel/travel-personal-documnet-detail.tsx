import { Card, CardContent } from "@/components/ui/card"
import TravelDocumentTable from "./travel-document-table"
import AddUpdateTravelDocumnetAction from "./add-update-travel-documnet-action"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import useUploadEmployeeTravelDocumentMutation from "@/api/mutations/upload-employee-travel-document.mutation"
import useUpdateEmployeeTravelDocumentMutation from "@/api/mutations/update-employee-travel-document"
import { PlusCircle } from "lucide-react"

const TravelPersonalDocumnetDetails = () => {
    const { personalDocumnets: documents, id: travelId } = useContext(TravelDetailContext)
    return (
        <Card className="w-1/1">
            <CardContent>
                <div className="w-1/1 flex justify-end ">
                    <AddUpdateTravelDocumnetAction Actionicon={PlusCircle} travelId={travelId} mutation={useUploadEmployeeTravelDocumentMutation} />
                </div>
                <TravelDocumentTable documents={documents} canUpdate={true} updateMutation={useUpdateEmployeeTravelDocumentMutation}/>
            </CardContent>
        </Card>
    )
}

export default TravelPersonalDocumnetDetails
