import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type";
import type { TGameSlotResponse } from "./TGameSlotResponse.type";

export type TSlotRequsetResponse = {
    id:number;
    status:string;
    gameSlot:TGameSlotResponse;
    requestedBy : TEmployeeWithNameOnly;
    slotRequestWiseEmployee: {employee:TEmployeeWithNameOnly}[];
}