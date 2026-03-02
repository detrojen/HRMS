import { useFetchGameInterests } from "@/api/queries/game-scheduling.queries"
import EmployeeWiseGameInterestList from "@/components/functionality/game-scheduling/employee-game-interest-list"
import SlotHistory from "@/components/functionality/game-scheduling/slot-history"
import { useAppDispatch } from "@/store/hooks"
import { setInitialData } from "@/store/slices/game-interest-slice"
import { Outlet } from "react-router-dom"

const GameSchedulingPage = () => {
    const {data} = useFetchGameInterests()
    const dispatch = useAppDispatch()
    if(data?.data.status === "OK"){
        dispatch(setInitialData(data.data.data))
    }
    return (
        <>
        <div className="w-1/1 min-h-full grid grid-cols-10  gap-2 p-2 ">
                <div className="col-span-6">
                    <Outlet />
                </div>
            <div className="flex flex-col gap-3 col-span-4">
                    <EmployeeWiseGameInterestList />
                    <SlotHistory />
                </div>
        </div>
    
           
        </>
    )
}

export default GameSchedulingPage