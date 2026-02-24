import { useFetchCurrentGameStatus } from "@/api/queries/game-scheduling.queries"
import GameCurrentStatusCard from "@/components/functionality/game-scheduling/game-current-status-card"

const GameHomePage = () => {
    const {data,isLoading} = useFetchCurrentGameStatus()
    if(isLoading) return "Loading data"
    return(
        <>
            <div className="grid grid-cols-1 gap-2">
                {data?.data.map(game=><GameCurrentStatusCard key={game.gameSlot.id} {...game}/>)}
            </div>
        </>
    )
}

export default GameHomePage