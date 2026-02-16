export type TUploadTravelDocumnetRequest = {
    documentDetails: {
        id: number
        type: string
        description: string
    },
    travelId: number
    file: File
}