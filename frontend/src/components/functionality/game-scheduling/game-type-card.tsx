import type { TGameType } from "@/types/apiResponseTypes/TGameType.type"
import { Card, CardContent, CardHeader } from "../../ui/card"
import { Link, useNavigate } from "react-router-dom"
import { useContext } from "react"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { Button } from "@/components/ui/button"


const GameTypeCard = ({ gameType }: { gameType: TGameType }) => {
    const navTo = useNavigate()
    const {user} = useContext(AuthContext)
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
                <div className="flex gap-2">
                    <Button onClick={()=>{navTo(`/game/book-slot?game-id=${gameType.id}`)}}>Book Slot</Button>
                    {user.role==="HR" && <Button onClick={()=>{navTo(`/game/types/${gameType.id}`)}}>Update</Button>}
                </div>
            </CardContent>
        </Card>
    )
}

export default GameTypeCard