import { useFetchDashboardData } from "@/api/queries/dashboard.queries"
import EmployeeMinDetailCard from "@/components/functionality/employee-min-detail-card"
import { Badge } from "@/components/ui/badge"
import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Item, ItemContent, ItemDescription, ItemTitle } from "@/components/ui/item"
import { useNavigate } from "react-router-dom"


const HomePage = () => {
    const navTo = useNavigate()
    const { data, isLoading } = useFetchDashboardData()
    const dashboard = data?.data
    if (isLoading) {
        return "Loading dashboard"
    }
    return <>

        <div className="grid grid-cols-10 w-full flex-col gap-2 p-3">
            <Card className="row-span-4 col-span-4">
                <CardHeader>
                    <p>Upcoming slots</p>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                    {
                        dashboard?.upcomingSlots.map
                            (
                                item => <Item variant="outline" onClick={() => navTo(`game/slots/requested/${item.id}`)}>
                                    <ItemContent>
                                        <ItemTitle> <Badge className={`${item.status == "Confirm" ? "bg-green-500" : "bg-orange-500"}`} variant={item.status == "Confirm" ? "default" : "ghost"}>{item.status}</Badge> <Badge variant={"outline"}>{item.gameSlot.gameType}</Badge> </ItemTitle>
                                        <ItemDescription>
                                            {item.gameSlot.startsFrom} to {item.gameSlot.endsAt}
                                        </ItemDescription>
                                    </ItemContent>

                                </Item>
                            )

                    }
                    </div>
                </CardContent>
            </Card>
            <Card className="col-span-6">
                <CardHeader>
                    <p>Upcoming travels</p>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                        {
                            dashboard?.upcomingTravels.map
                                (
                                    item => <Item variant="outline" onClick={() => navTo(`travels/${item.id}`)}>
                                        <ItemContent>
                                            <ItemTitle> {item.title} </ItemTitle>
                                            <ItemDescription>
                                                {item.startDate.toString()} to {item.endDate.toString()}
                                            </ItemDescription>
                                        </ItemContent>

                                    </Item>
                                )

                        }
                    </div>
                </CardContent>
            </Card>
            <Card className="col-span-3">
                <CardHeader>
                    <p>Todays birthday</p>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                    {

                        dashboard?.todayBirthdayPersons.map
                            (
                                person => <Item variant="outline" >
                                    <ItemContent>
                                        <EmployeeMinDetailCard {...person} key={person.id} />                                   </ItemContent>

                                </Item>
                            )

                    }
                    </div>
                </CardContent>
            </Card>
            <Card className="col-span-3">
                <CardHeader>
                    <p>Todays Work aniversory</p>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                    {
                        dashboard?.todayWorkAnniversaryPersons.map
                            (
                                person => <Item variant="outline">
                                    <ItemContent>
                                        <EmployeeMinDetailCard {...person} key={person.id} />                                   </ItemContent>

                                </Item>
                            )

                    }
                    </div>
                </CardContent>
            </Card>



        </div>
    </>
}



export default HomePage