import { useFetchGameTypes } from "@/api/queries/game-scheduling.queries"
import GameTypeCard from "./game-type-card"

const GameTypeList = () => {
    const {data:gameTypeQueryData} = useFetchGameTypes()
    const gameTypes = gameTypeQueryData?.data.data
    return (
        <>
        <div className="grid grid-cols-2 gap-2">
            {
                gameTypes?.map(item=><GameTypeCard gameType={item}/>)
            }
        </div>
        
        </>
    )
}

export default GameTypeList