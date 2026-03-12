import { useFetchDashboardData } from "@/api/queries/dashboard.queries"
import EmployeeMinDetailCard from "@/components/functionality/employee-min-detail-card"
import { Badge } from "@/components/ui/badge"
import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Item, ItemActions, ItemContent, ItemDescription, ItemTitle } from "@/components/ui/item"
import { ScrollArea } from "@/components/ui/scroll-area"
import { useNavigate } from "react-router-dom"


const HomePage = () => {
    const navTo = useNavigate()
    const { data, isLoading } = useFetchDashboardData()
    const dashboard = data?.data.data
    if (isLoading) {
        return "Loading dashboard"
    }
    return <>

        <div className="grid grid-cols-10 w-full gap-2 p-3 min-h-1/1">
            <Card className="row-span-2 h-1/1 col-span-4">
                <CardHeader>
                    <p>Upcoming slots</p>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                        {dashboard && dashboard?.upcomingSlots.length > 0 ?
                            dashboard?.upcomingSlots.map(
                                item => <Item variant="outline" onClick={() => navTo(`game/slots/requested/${item.id}`)}>
                                    <ItemContent>
                                        <ItemTitle> <Badge className={`${item.status == "Confirm" ? "bg-green-500" : "bg-orange-500"}`} variant={item.status == "Confirm" ? "default" : "ghost"}>{item.status}</Badge> <Badge variant={"outline"}>{item.gameSlot.gameType}</Badge> </ItemTitle>
                                        <ItemDescription>
                                            {item.gameSlot.startsFrom} to {item.gameSlot.endsAt}
                                        </ItemDescription>
                                    </ItemContent>

                                </Item>
                            ) : <div className="text-center">
                                <p>No Active slots</p>
                            </div>

                        }
                    </div>
                </CardContent>
            </Card>
            <Card className="col-span-6 flex flex-col  h-fit">
                <CardHeader>
                    <p>Upcoming travels</p>
                </CardHeader>
                <CardContent className="h-1/1">
                    <ScrollArea className="overflow-y-scroll min-h-50 max-h-100 flex flex-col gap-2">
                        <div className="flex flex-col gap-2">
                            {dashboard && dashboard.upcomingTravels.length > 0 ?
                                dashboard?.upcomingTravels.map(
                                    item => <Item variant="outline" onClick={() => navTo(`travels/${item.id}`)}>
                                        <ItemContent >
                                            <ItemTitle> {item.title} </ItemTitle>
                                            <ItemDescription>
                                                {item.startDate.toString()} to {item.endDate.toString()}
                                            </ItemDescription>

                                        </ItemContent>
                                        <ItemActions>
                                            <Badge className={"travel-status-" + item.status.toLowerCase()}>{item.status}</Badge>
                                        </ItemActions>

                                    </Item>
                                ) : <div className="text-center">
                                    <p>No upcoming travels</p>
                                </div>

                            }
                        </div>
                    </ScrollArea>
                </CardContent>
            </Card>
            <Card className="col-span-3">
                <CardHeader>
                    <p>Todays birthday</p>
                </CardHeader>
                <CardContent>
                    <div className="flex flex-col gap-2">
                        {
                            dashboard && dashboard?.todayBirthdayPersons.length > 0 ?
                                dashboard?.todayBirthdayPersons.map(
                                    person => <Item variant="outline" >
                                        <ItemContent>
                                            <EmployeeMinDetailCard {...person} key={person.id} />                                   </ItemContent>

                                    </Item>
                                ) : <div className="text-center">
                                    <p>No party today</p>
                                </div>

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
                            dashboard && dashboard?.todayWorkAnniversaryPersons.length>0?
                            dashboard?.todayWorkAnniversaryPersons.map(
                                person => <Item variant="outline">
                                    <ItemContent>
                                        <EmployeeMinDetailCard {...person} key={person.id} />                                   </ItemContent>
                                </Item>
                            ): <div className="text-center">
                                    <p>No party today</p>
                                </div>

                        }
                    </div>
                </CardContent>
            </Card>



        </div>
    </>
}



export default HomePage