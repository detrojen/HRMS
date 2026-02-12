import type { TGameType } from "@/types/apiResponseTypes/TGameType.type"
import { Card, CardContent, CardHeader } from "../../ui/card"
import { Link } from "react-router-dom"


const GameTypeCard = ({ gameType }: { gameType: TGameType }) => {
    
    return (
        <Card >
            <CardHeader>
                <h4 className="scroll-m-20 text-xl font-semibold tracking-tight">
                    {gameType.game}
                </h4>

            </CardHeader>
            <CardContent>
                <p className="leading-7 [&:not(:first-child)]:mt-6">total interested employee:- {gameType.noOfInteretedEmployees}</p>
                <p>Slot Duration:- {gameType.slotDuration} minutes</p>
                <p>play hours:- {gameType.openingHours.toString()} to {gameType.closingHours.toString()}</p>
                <Link className="text-blue-500" to={`${gameType.id}`}>get more...</Link>
            </CardContent>
        </Card>
    )
}

export default GameTypeCard