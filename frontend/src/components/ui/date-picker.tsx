"use client"

import * as React from "react"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Field, FieldLabel } from "@/components/ui/field"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"
import { format } from "date-fns"

export function DatePicker({title,onSelect,value}:{title:string,onSelect:(date:Date)=>void, value?:Date|null}) {
  const [date, setDate] = React.useState<Date|undefined | null>(value)

  return (
    <>
      <FieldLabel htmlFor="date-picker-simple">{title}</FieldLabel>
      <Popover>
        <PopoverTrigger asChild>
          <Button
            variant="outline"
            id="date-picker-simple"
            className="justify-start font-normal"
          >
            {date ? format(date, "PPP") : <span>Pick a date</span>}
          </Button>
        </PopoverTrigger>
        <PopoverContent className="w-auto p-0" align="start">
          <Calendar
            mode="single"
            selected={date??undefined}
            onSelect={(value)=>{
                setDate(value)
                onSelect(value!)
            }}
            defaultMonth={date??undefined}
          />
        </PopoverContent>
      </Popover>
      </>
  )
}
