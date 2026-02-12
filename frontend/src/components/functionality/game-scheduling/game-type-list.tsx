import { useFetchGameTypes } from "@/api/queries/game-scheduling.queries"
import GameTypeCard from "./game-type-card"

const GameTypeList = () => {
    const {data} = useFetchGameTypes()
    return (
        <>
        <div className="grid grid-cols-2 gap-2">
            {
                data?.map(item=><GameTypeCard gameType={item}/>)
            }
        </div>
        
        </>
    )
}

export default GameTypeList