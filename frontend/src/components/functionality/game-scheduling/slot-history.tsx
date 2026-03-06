import {  useFetchSlotRequests } from "@/api/queries/game-scheduling.queries";
import { Card } from "../../ui/card";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "../../ui/select";
import { Item, ItemContent, ItemDescription, ItemTitle } from "../../ui/item";
import { Badge } from "../../ui/badge";
import { useNavigate } from "react-router-dom";
import useDebounce from "@/hooks/use-debounce";
import { useState } from "react";
import { ScrollArea } from "@/components/ui/scroll-area";

const SlotHistory = () => {
    const navTo = useNavigate();
    const [slots, setSlots] = useState("active")
    const { data } = useFetchSlotRequests(useDebounce({ slots }, 100));
    const slotRequests = data?.data.data
    return (
        <>

            <Card className="p-2">
                <Select defaultValue={slots} onValueChange={(value) => { setSlots(value) }}>
                    <SelectTrigger className="w-full max-w-48">
                        <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectLabel>Select history</SelectLabel>
                            <SelectItem value="active" >upcoming</SelectItem>
                            <SelectItem value="last 7 days">Last 7 days</SelectItem>
                            <SelectItem value="this week">This week</SelectItem>
                            <SelectItem value="this month">This month</SelectItem>
                            <SelectItem value="previous month">Previous month</SelectItem>
                        </SelectGroup>
                    </SelectContent>
                </Select>
                <ScrollArea className="max-h-100 z-0">
                    <div className="flex w-full max-w-md flex-col gap-2">
                        {
                            slotRequests && slotRequests.map(
                                item => <Item variant="outline" onClick={() => navTo(`slots/requested/${item.id}`)}>
                                    <ItemContent>
                                        <ItemTitle> <Badge className={`${item.status == "Confirm" ? "bg-green-500" : "bg-orange-500"}`} variant={item.status == "Confirm" ? "default" : "ghost"}>{item.status}</Badge> <Badge variant={"outline"}>{item.gameSlot.gameType}</Badge> </ItemTitle>
                                        <ItemDescription>
                                           {item.gameSlot.slotDate} 
                                           <br></br>
                                           {item.gameSlot.startsFrom} to {item.gameSlot.endsAt}
                                        </ItemDescription>
                                    </ItemContent>

                                </Item>
                            )
                        }
                    </div>

                </ScrollArea>
            </Card>
        </>
    )
}

export default SlotHistory

