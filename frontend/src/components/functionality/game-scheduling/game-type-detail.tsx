import { useParams } from "react-router-dom"
import GameTypeForm from "./game-type-form"
import { useFetchGameTypeById } from "@/api/queries/game-scheduling.queries"
import { useState } from "react"
import { Button } from "../../ui/button"

const GameTypeDetail = () => {
    const {gameTypeId} = useParams()
    const {data:gameType} = useFetchGameTypeById(gameTypeId!)
    const [isEditable, setIsEditable] = useState(false);
    return (
        <>
           <div>
                <Button onClick={()=>setIsEditable(!isEditable)}>Enable editing</Button>
                 {gameType && <GameTypeForm gameType={gameType??null} isEditable={isEditable}/>}
           </div>
            
        </>
    )
}

export default GameTypeDetail