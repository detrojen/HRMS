export type TCreatePostRequest = {
    postDetails: TPostDetails
    attachment: File
}

type TPostDetails ={
    title: string,
    body : string,
    tags: string[]
}