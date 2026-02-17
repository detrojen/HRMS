import { Card, CardContent } from "@/components/ui/card"
import TravelDocumentTable from "./travel-document-table"
import { useContext } from "react"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"

const TravelEmployeeDocumnetDetails = () => {
    const { employeeDocuments: documents } = useContext(TravelDetailContext)

    return (
        <Card className="w-1/1">
            <CardContent>
                <TravelDocumentTable documents={documents} canUpdate={false} />
            </CardContent>
        </Card>
    )
}

export default TravelEmployeeDocumnetDetails