import { useFetchActiveSlots } from "@/api/queries/game-scheduling.queries";
import { Card } from "../ui/card";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "../ui/select";
import { Item, ItemActions, ItemContent, ItemDescription, ItemTitle } from "../ui/item";
import { Button } from "../ui/button";
import { Badge } from "../ui/badge";
import { useCancelSlotMutation } from "@/api/mutations/cancelRequest.mutation";

const SlotHistory = () => {
    const { data } = useFetchActiveSlots();
    const cancelRequestMutation = useCancelSlotMutation();
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
                        data && data.data.map(
                            item => <Item variant="outline">
                                <ItemContent>
                                    <ItemTitle>{item.id} <Badge variant={item.status=="Confirm"?"default":"ghost"}>{item.status}</Badge> </ItemTitle>
                                    <ItemDescription>
                                        {item.gameSlot.startsFrom} to {item.gameSlot.endsAt}
                                    </ItemDescription>
                                </ItemContent>
                                <ItemActions>
                                    <Button variant={"destructive"} onClick={()=>cancelRequestMutation.mutate(item.id)}>Cancel</Button>
                                </ItemActions>
                            </Item>
                        )
                    }



                </div>
            </Card>
        </>
    )
}

export default SlotHistory

