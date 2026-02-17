import { useParams } from "react-router-dom"
import GameTypeForm from "./game-type-form"
import { useFetchGameTypeById } from "@/api/queries/game-scheduling.queries"
import { useContext, useState } from "react"
import { Button } from "../../ui/button"
import { AuthContext } from "@/contexts/AuthContextProvider"

const GameTypeDetail = () => {
    const {gameTypeId} = useParams()
    const {data:gameType} = useFetchGameTypeById(gameTypeId!)
    const [isEditable, setIsEditable] = useState(false);
    const {user} = useContext(AuthContext)
    return (
        <>
           <div>
                {user.role==="HR"&&<Button onClick={()=>setIsEditable(!isEditable)}>Enable editing</Button>}
                 {gameType && <GameTypeForm gameType={gameType??null} isEditable={isEditable}/>}
           </div>
            
        </>
    )
}

export default GameTypeDetail