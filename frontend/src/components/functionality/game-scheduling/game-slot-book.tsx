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

  return (
    <>

      <Card className="p-1 h-full">
        <div className="flex gap-1">
          <div>
            <DatePicker title={"Date"} onSelect={function (value: Date): void {
              searchParams.set("slotDate", `${value.getFullYear()}-${(value.getMonth() + 1).toString().padStart(2, "0")}-${(value.getDate()).toString().padStart(2, "0")}`)
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
              <Field>
                <FieldLabel >Add player</FieldLabel>
                <Input
                  id="search-player"
                  type="text"
                  placeholder="search player"
                  onChange={(e) => { setNameQuery(e.target.value) }}
                />
              </Field>
              {
                interestedEmployees?.map(e => <h1 onClick={() => { setNameQuery(""); setPlayers([...plyers, e]) }}>{e.firstName}</h1>)
              }
              <Separator></Separator>
              {
                plyers.map(player => <div className="flex gap-5">
                  <h1>{player.firstName} </h1>
                  <Button size={"xs"} onClick={() => {
                    setPlayers(plyers.filter(p => p.id != player.id))
                  }}>remove</Button>
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