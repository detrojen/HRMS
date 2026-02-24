export type TCreatePostRequest = {
    postDetails: TPostDetails
    attachment: File
}

type TPostDetails ={
    id?:number
    title: string,
    body : string,
    tags: string[]
}