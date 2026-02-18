export type TUploadTravelDocumnetRequest = {
    documentDetails: {
        id: number
        type: string
        description: string
        documentPath?: string
    },
    travelId: number
    file?: File
}