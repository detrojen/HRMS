import { useFetchInterestedEmployeeByNameLike, useQueryGameSlots } from "@/api/queries/game-scheduling.queries"
import { useState } from "react"
import { useParams, useSearchParams } from "react-router-dom"
import { Card } from "../ui/card"
import { Field, FieldLabel } from "../ui/field"
import { Input } from "../ui/input"
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type"
import { Button } from "../ui/button"
import useRequestSlotMutation from "@/api/mutations/request-slot.mutation"
function getSlotAppearnceClass(slotStatus:boolean){
  return slotStatus ? "available-slot" : "unavailable-slot"
}
const GameSlotList = () => {
    const {gameTypeId} = useParams()
    const [searchParams] = useSearchParams()
    const [slotDate,setSlotDate] = useState(searchParams.get("slotDate")??(new Date()).toLocaleDateString('en-CA'))
    const {data} = useQueryGameSlots({gameTypeId,date:slotDate})
    const [selectedSlot, setSelectedSlot] = useState(0)
    const [nameQuery, setNameQuery] = useState("")
    const [plyers,setPlayers] = useState<TEmployeeWithNameOnly[]>([]);
    const {data:interestedEmployees} = useFetchInterestedEmployeeByNameLike({gameTypeId,nameLike:nameQuery})
    const requestSlotMutation = useRequestSlotMutation()
    return (
        <>
           
           <Card className="p-1 h-full">
            <div className="grid grid-cols-15 gap-1">
                {data && data.map(item => <div key={item.id} className={`aspect-square ${selectedSlot == item.id? "selected-slot": getSlotAppearnceClass(item.available)}`} onClick={()=>{setSelectedSlot( item.id)}}></div>)}
            </div>
            <Field>
                <FieldLabel htmlFor="email">Add player</FieldLabel>
                <Input
                  id="search-player"
                  type="text"
                  placeholder="search player"
                  onChange={(e)=>{setNameQuery(e.target.value)}}
                />
              </Field>
              {
                // JSON.stringify(interestedEmployees)
                interestedEmployees?.map(e=><h1 onClick={()=>{setNameQuery(""); setPlayers([...plyers,e])}}>{e.firstName}</h1>)
              }

              {
                plyers.map(player=><h1>{player.firstName}</h1>)
              }

              <Button variant={"secondary"} 
                onClick={()=>{
                    requestSlotMutation.mutate({
                        slotId: selectedSlot,
                        otherPlayersId: plyers.map(player=>player.id)
                    })
                }}
              >Request slot</Button>
        
           </Card>
        </>
    )
}

export default GameSlotList