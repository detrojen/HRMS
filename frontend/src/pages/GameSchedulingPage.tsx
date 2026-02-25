import { useFetchGameInterests } from "@/api/queries/game-scheduling.queries"
import EmployeeWiseGameInterestList from "@/components/functionality/game-scheduling/employee-game-interest-list"
import SlotHistory from "@/components/functionality/game-scheduling/slot-history"
import { Card } from "@/components/ui/card"
import { Outlet } from "react-router-dom"

const GameSchedulingPage = () => {
    const {} = useFetchGameInterests()
    return (
        <>
            <div className="-z-10 w-1/1 max-h-full p-2 grid grid-cols-10 gap-2 relative ">
                <div className="col-span-6">
                    <Outlet />
                </div>
                <div className="flex flex-col gap-3 col-span-4 sticky top-0">
                    <EmployeeWiseGameInterestList />
                    <SlotHistory />
                </div>
            </div>
           
        </>
    )
}

export default GameSchedulingPage