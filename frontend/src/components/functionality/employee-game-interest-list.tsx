import { BadgeCheckIcon, ChevronRightIcon } from "lucide-react"
import { Card, CardHeader } from "../ui/card"
import { Item, ItemActions, ItemContent, ItemDescription, ItemMedia, ItemTitle } from "../ui/item"
import { Button } from "../ui/button"
import { Checkbox } from "../ui/checkbox"

const  EmployeeWiseGameInterestList = () => {
    return (
        <>
            <Card className="p-2">
                <CardHeader>
                    Games
                </CardHeader>
                <div className="flex w-full max-w-md flex-col gap-2">
                    <Item variant="outline">
                        <ItemContent>
                            <ItemTitle>Chess</ItemTitle>
                        </ItemContent>
                        <ItemActions>
                            <Checkbox id="terms-checkbox" name="terms-checkbox" value={1} />
                        </ItemActions>
                    </Item>
                    <Item variant="outline">
                        <ItemContent>
                            <ItemTitle>Pool</ItemTitle>
                        </ItemContent>
                        <ItemActions>
                            <Checkbox id="terms-checkbox" name="terms-checkbox"  />
                        </ItemActions>
                    </Item>
                    <Item variant="outline">
                        <ItemContent>
                            <ItemTitle>Foosbal</ItemTitle>
                        </ItemContent>
                        <ItemActions>
                            <Checkbox id="terms-checkbox" name="terms-checkbox"  />
                        </ItemActions>
                    </Item>
                    <Item variant="outline">
                        <ItemContent>
                            <ItemTitle>Carrom</ItemTitle>
                        </ItemContent>
                        <ItemActions>
                            <Checkbox id="terms-checkbox" name="terms-checkbox"  />
                        </ItemActions>
                    </Item>
                    
                </div>
            </Card>
        </>
    )
}

export default EmployeeWiseGameInterestList