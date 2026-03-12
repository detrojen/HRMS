import { Card, CardContent } from "@/components/ui/card";
import AddUpdateTravelDocumnetAction from "./add-update-travel-documnet-action";
import TravelDocumentTable from "./travel-document-table";
import { AuthContext } from "@/contexts/AuthContextProvider";
import { useContext } from "react";
import { TravelDetailContext } from "@/contexts/TravelDetailContext";
import useUploadTravelDocumentMutation from "@/api/mutations/upload-travel-document.mutation";
import { Plus } from "lucide-react";
import useUpdateTravelDocumentMutation from "@/api/mutations/update-travel-document.mutation";
import useDeleteTravelDocumnetMutation from "@/api/mutations/delete-travel-documnet.mutation";

const TravelDocumnetDetails = () => {
    const { user } = useContext(AuthContext);
    const { travelDocuments: documents, id: travelId ,status} = useContext(TravelDetailContext)
    const canAddOrModify = user.role == "HR" && (status==="STARTED" || status==="INITIATED")
    return (
        <Card className="w-1/1">
            <CardContent>
                {canAddOrModify ? <div className="w-1/1 flex justify-end ">
                    <AddUpdateTravelDocumnetAction travelId={travelId} mutation={useUploadTravelDocumentMutation} Actionicon={Plus} />
                </div> : <></>}
                <TravelDocumentTable deleteMutation={useDeleteTravelDocumnetMutation} updateMutation={useUpdateTravelDocumentMutation} documents={documents} canAddOrModify={canAddOrModify} />
            </CardContent>
        </Card>
    )
}

export default TravelDocumnetDetails