import EmployeeWiseGameInterestList from "@/components/functionality/employee-game-interest-list"
import GameSlotList from "@/components/functionality/game-slot-list"
import SlotHistory from "@/components/functionality/slot-history"
import { Card } from "@/components/ui/card"
import { Outlet } from "react-router-dom"

const GameSchedulingPage = () => {
    
    return (
        <>
            <Card className="w-1/1 max-h-full p-2 grid grid-cols-10 gap-2">
                <div className="col-span-6">
                    <Outlet />
                </div>
                <div className="flex flex-col gap-3 col-span-4">
                    <EmployeeWiseGameInterestList />
                    <SlotHistory />
                </div>
            </Card>
           
        </>
    )
}

export default GameSchedulingPage