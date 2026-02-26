import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import type { TCurrentGameStatusResponse } from "@/types/apiResponseTypes/TCurrentGameStatusResponse.type"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import { Badge } from "@/components/ui/badge"

const GameCurrentStatusCard = (props: TCurrentGameStatusResponse) => {
    return (
        <Card className="w-1/1 grow basis-0">
            <CardHeader className="flex justify-between">
                <CardTitle>
                    {props.gameSlot.gameType}
                </CardTitle>
                <Badge variant={props.gameSlot.available? "default":"destructive"} >{props.gameSlot.available ? "Free" : "Occupied"}</Badge>

            </CardHeader>
            <CardContent className="flex flex-col gap-4">
                <img className="rounded-2xl" src={`/api/resource/games/${props.gameSlot.gameType}.png`}></img>
                <div className="grid grid-cols-2 gap-2">
                    {props.players.map(player => <EmployeeMinDetailCard id={player.id} key={player.email} firstName={player.firstName} lastName={player.lastName} designation={player.designation} />)}
                </div>
                <div>
                    <p>Slot Details</p>
                    <ul>
                        <li>Date: {props.gameSlot.slotDate.toString()}</li>
                        <li>Time: {props.gameSlot.startsFrom} - {props.gameSlot.endsAt}</li>
                    </ul>
                </div>
            </CardContent>
        </Card>
    )
}

export default GameCurrentStatusCard