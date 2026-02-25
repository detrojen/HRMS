import type { TEmployeeMinDetail } from "../TEmployeeMinDetail.type";
import type { TRequestedSlotDetail } from "./TRequestedSlotDetail.type";
import type { TTravelMinDetail } from "./TTravelMinDetails.type";

export type TDashboardDataResponse = {
    upcomingSlots: TRequestedSlotDetail[],
    todayBirthdayPersons: TEmployeeMinDetail[],
    todayWorkAnniversaryPersons: TEmployeeMinDetail[],
    upcomingTravels: TTravelMinDetail[],
}