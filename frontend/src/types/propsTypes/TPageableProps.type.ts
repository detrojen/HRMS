import type { ReactNode } from "react"
import type { TPageableResponse } from "../apiResponseTypes/TPageableResponse.type"

export type TPageableProps<T> = {
    data: TPageableResponse<T>
    render: (item:T)=>ReactNode
}