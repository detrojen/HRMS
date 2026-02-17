import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { TravelDetailContext } from "@/contexts/TravelDetailContext"
import { useContext } from "react"

const TravelBasicDetail = () => {
    const { descripton, startDate, endDate, lastDateToSubmitExpense, title } = useContext(TravelDetailContext)

    return (
        <Card>
            <CardHeader>
                <h1>{title}</h1>
            </CardHeader>
            <CardContent>
                <div className="flex flex-col gap-1 ">
                    <p>Start date:- {startDate}</p>
                    <p>End date:- {endDate}</p>
                    <p>Expense accepted till :- {lastDateToSubmitExpense}</p>
                </div>
                <div className="my-2">
                    <h2>Description</h2>
                    <div className="px-2">
                        {descripton}
                    </div>
                </div>
            </CardContent>
        </Card>
    )
}
export default TravelBasicDetail