import type { TEmployeeWithNameOnly } from "../TEmployeeWithNameOnly.type"

export type TTravelDoucmentResponse = {
    id:  number
    type: string
    documentPath: string
    description: string
    uploadedBy: TEmployeeWithNameOnly
}