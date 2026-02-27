import { useFetchInterestedEmployeeByNameLike, useQueryGameSlots } from "@/api/queries/game-scheduling.queries"
import { useState } from "react"
import { useParams, useSearchParams } from "react-router-dom"
import { Card } from "../../ui/card"
import { Field, FieldLabel } from "../../ui/field"
import { Input } from "../../ui/input"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"
import { Button } from "../../ui/button"
import useRequestSlotMutation from "@/api/mutations/request-slot.mutation"
import { Separator } from "@/components/ui/separator"
import { Slot } from "radix-ui"
import { DatePicker } from "@/components/ui/date-picker"
import { useAppSelector } from "@/store/hooks"
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select"
import { format } from "date-fns"
import Searchable from "@/components/ui/searchable"
function getSlotAppearnceClass(slotStatus: boolean) {
  return slotStatus ? "available-slot" : "unavailable-slot"
}
const GameSlotBook = () => {
  const [searchParams, setSearchParams] = useSearchParams()
  const gameId = searchParams.get("game-id")
  const { data } = useQueryGameSlots({ gameTypeId: gameId ?? "0", date: searchParams.get("slotDate") ?? (new Date()).toLocaleDateString('en-CA') })
  const [selectedSlot, setSelectedSlot] = useState(0)
  const [nameQuery, setNameQuery] = useState("")
  const [plyers, setPlayers] = useState<TEmployeeWithNameOnly[]>([]);
  const { data: interestedEmployees } = useFetchInterestedEmployeeByNameLike({ gameTypeId: gameId ?? "0", nameLike: nameQuery })
  const requestSlotMutation = useRequestSlotMutation()
  const games = useAppSelector((state) => state.gameInterest.filter(item => item.interested).map((item) => {
    return { id: item.gameTypeId, game: item.gameType }
  }))
  if (games.find(game => game.id == Number(searchParams.get("game-id")) || searchParams.get("game-id") == null) == undefined) {
    return "Please first selects that game as interested"
  }
  return (
    <>

      <Card className="p-1 h-full">
        <div className="flex gap-1">
          <div>
            <DatePicker title={"Date"} onSelect={function (value: Date): void {
              searchParams.set("slotDate", format(value, "yyyy-MM-dd"))
              setSearchParams(searchParams)
            }} />
          </div>

          <Select value={gameId ?? undefined} onValueChange={(value) => {
            searchParams.set("game-id", value)
            setSearchParams(searchParams)
          }}>
            <SelectTrigger className="w-full max-w-48">
              <SelectValue placeholder="Select a game" />
            </SelectTrigger>
            <SelectContent>
              <SelectGroup>
                <SelectLabel>game</SelectLabel>
                {games.map(game => <SelectItem value={`${game.id}`}>{game.game}</SelectItem>)}
              </SelectGroup>
            </SelectContent>
          </Select>

        </div>
        {
          gameId === null ?
            <h1>You need to choose one game to book slot</h1> :
            <>

              <div className="flex flex-row gap-2 flex-wrap">
                {data && data.map(item => <div key={item.id} className={`w-fit p-2 ${selectedSlot == item.id ? "selected-slot" : getSlotAppearnceClass(item.available)}`} onClick={() => { setSelectedSlot(item.id) }}>{item.startsFrom}</div>)}
              </div>
            
              <Searchable
                data={interestedEmployees}
                setQuery={setNameQuery}
                onSelectItem={(player) => { setNameQuery(""); setPlayers([...plyers, player]) }}
                render={(player) => <h1>{player.firstName} {player.lastName}</h1>}
              >
                <Button>Add Players</Button>
              </Searchable>
              <Separator></Separator>
              {
                plyers.map(player => <div className="flex gap-5">
                  <Button size={"xs"} onClick={() => {
                    setPlayers(plyers.filter(p => p.id != player.id))
                  }}>remove</Button>
                  <h1>{player.firstName} {player.lastName}</h1>

                </div>
                )
              }

              <Button variant={"secondary"}
                onClick={() => {
                  requestSlotMutation.mutate({
                    slotId: selectedSlot,
                    otherPlayersId: plyers.map(player => player.id)
                  })
                }}
              >Request slot</Button>
            </>
        }

      </Card>
    </>
  )
}

export default GameSlotBook