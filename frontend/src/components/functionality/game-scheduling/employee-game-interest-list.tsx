import { BadgeCheckIcon, ChevronRightIcon } from "lucide-react"
import { Card, CardHeader } from "../../ui/card"
import { Item, ItemActions, ItemContent, ItemDescription, ItemMedia, ItemTitle } from "../../ui/item"
import { Button } from "../../ui/button"
import { Checkbox } from "../../ui/checkbox"
import { useAppSelector } from "@/store/hooks"
import { Link } from "react-router-dom"
import updateGameInterestMutation from "@/api/mutations/update-game-interest.mutation"
import type { TUpdateGameInterest } from "@/types/apiRequestTypes/TUpdateGameInterest.type"

const  EmployeeWiseGameInterestList = () => {
    const updateGameInterest = updateGameInterestMutation()
    const data = useAppSelector(state=>state.gameInterest)
    const handleUpdate = (data: TUpdateGameInterest) => {
        updateGameInterest.mutate(data)
    }
    return (
        <>
            <Card className="p-2">
                <CardHeader>
                    Games
                </CardHeader>
                <div className="flex w-full max-w-md flex-col gap-2">
                    {data.map(item=><Item key={item.id} variant="outline">
                        <ItemContent>
                            <ItemTitle><Link to={`book-slot?game-id=${item.gameTypeId}`}>{item.gameType}</Link></ItemTitle>
                        </ItemContent>
                        <ItemActions>
                            <Checkbox id="terms-checkbox" name="terms-checkbox" defaultChecked={item.interested} onCheckedChange={(value:boolean)=>{
                                handleUpdate({gameTypeId:item.gameTypeId, isInterested:value})
                            }}/>
                        </ItemActions>
                    </Item>)}
                    
                </div>
            </Card>
        </>
    )
}

export default EmployeeWiseGameInterestList