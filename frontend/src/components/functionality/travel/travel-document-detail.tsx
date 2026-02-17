import { Card, CardContent } from "@/components/ui/card";
import AddTravelDocumnetAction from "./add-travel-documnet-action";
import TravelDocumentTable from "./travel-document-table";
import { AuthContext } from "@/contexts/AuthContextProvider";
import { useContext } from "react";
import { TravelDetailContext } from "@/contexts/TravelDetailContext";
import useUploadTravelDocumentMutation from "@/api/mutations/upload-travel-document.mutation";

const TravelDocumnetDetails = () => {
    const { user } = useContext(AuthContext);
    const { travelDocuments: documents, id: travelId } = useContext(TravelDetailContext)
    return (
        <Card className="w-1/1">
            <CardContent>
                {user.role == "HR" ? <div className="w-1/1 flex justify-end ">
                    <AddTravelDocumnetAction travelId={travelId} mutation={useUploadTravelDocumentMutation} />
                </div> : <></>}
                <TravelDocumentTable documents={documents} canUpdate={user.role == "HR"} />
            </CardContent>
        </Card>
    )
}

export default TravelDocumnetDetails