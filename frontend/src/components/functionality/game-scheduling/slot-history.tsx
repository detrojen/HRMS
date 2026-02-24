import { useFetchActiveSlots } from "@/api/queries/game-scheduling.queries";
import { Card } from "../../ui/card";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "../../ui/select";
import { Item, ItemContent, ItemDescription, ItemTitle } from "../../ui/item";
import { Badge } from "../../ui/badge";
import { useNavigate } from "react-router-dom";

const SlotHistory = () => {
    const navTo = useNavigate();
    const { data } = useFetchActiveSlots();
    return (
        <>

            <Card className="p-2">
                <Select value="apple">
                    <SelectTrigger className="w-full max-w-48">
                        <SelectValue placeholder="Select a fruit" />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectLabel>Select history</SelectLabel>
                            <SelectItem value="apple" >upcoming</SelectItem>
                            <SelectItem value="banana">previous day</SelectItem>
                            <SelectItem value="blueberry">this month</SelectItem>
                        </SelectGroup>
                    </SelectContent>
                </Select>
                {/* {JSON.stringify(data)} */}
                <div className="flex w-full max-w-md flex-col gap-2">
                    {
                        data && data.map(
                            item => <Item variant="outline" onClick={()=>navTo(`slots/requested/${item.id}`)}>
                                <ItemContent>
                                    <ItemTitle> <Badge className={`${item.status=="Confirm"?"bg-green-500":"bg-orange-500"}`} variant={item.status=="Confirm"?"default":"ghost"}>{item.status}</Badge> <Badge variant={"outline"}>{item.gameSlot.gameType}</Badge> </ItemTitle>
                                    <ItemDescription>
                                        {item.gameSlot.startsFrom} to {item.gameSlot.endsAt}
                                    </ItemDescription>
                                </ItemContent>
                               
                            </Item>
                        )
                    }



                </div>
            </Card>
        </>
    )
}

export default SlotHistory

