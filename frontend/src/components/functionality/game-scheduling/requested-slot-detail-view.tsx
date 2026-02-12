
import { useFetctRequestedSlotDetail } from "@/api/queries/game-scheduling.queries"
import { useParams } from "react-router-dom"
import { Card, CardContent, CardHeader } from "../../ui/card"
import { Badge } from "../../ui/badge"
import { Item, ItemContent, ItemDescription, ItemTitle } from "../../ui/item"

const RequestedSlotDetail = () => {
    const {requestedSlotId} = useParams()
    
    const {data:slotDetail} = useFetctRequestedSlotDetail(requestedSlotId!)
    
    return (
        <Card className="p-1">
            <CardHeader>
                <h2>Slot Detail</h2>
                
            </CardHeader>
            <CardContent>
                    <div className="flex flex-col gap-1">
                        <ul className="mb-1 ml-6 list-disc [&>li]:mt-2">
                            <li>Game:- {slotDetail?.gameSlot.gameType}</li>
                            <li>Slot date:- {slotDetail?.gameSlot.slotDate}</li>
                            <li>Slot Time:- {slotDetail?.gameSlot.startsFrom} to {slotDetail?.gameSlot.endsAt}</li>
                        </ul>
                        <p>Players:-</p>
                        {
                            slotDetail?.slotRequestWiseEmployee.map(obj=>
                                <Item variant="outline" className="bg-red-100">
                                    <ItemContent>
                                        <ItemTitle>{obj.employee.firstName}  {obj.employee.lastName}</ItemTitle>
                                        {obj.employee.id == slotDetail.requestedBy.id?<ItemDescription>Slot requested by this person</ItemDescription> : <></>}
                                    </ItemContent>
                                </Item>
                            )
                        }
                        <Badge className={`${slotDetail?.status=="Confirm"?"bg-green-500":"bg-orange-500"}`} variant={"default"}>{slotDetail?.status}</Badge>
                    </div>
            </CardContent>
        </Card>
    )
}

export default RequestedSlotDetail