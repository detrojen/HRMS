import { useQuery } from "@tanstack/react-query";
import { fetchPostById, fetchPosts, fetchPostUploadedBySelf } from "../services/post.service";

export const useFetchPosts = ({page,limit,query,postTo,postFrom}:{page?:string,limit?:string,query?:string,postTo?:string,postFrom?:string}) => useQuery(
    {
        queryKey:["posts",{page,limit,query,postTo,postFrom}],
        queryFn: ()=>fetchPosts({page,limit,query,postTo,postFrom})
    }
)

export const usePostUploadedBySelf = ({page,limit,query,postTo,postFrom}:{page?:string,limit?:string,query?:string,postTo?:string,postFrom?:string}) => useQuery(
    {
        queryKey:["posts",{page,limit,query,postTo,postFrom},"post-uploaded-by-self"],
        queryFn: ()=>fetchPostUploadedBySelf({page,limit,query,postTo,postFrom})
    }
)

export const usePostById = (postId:number) => useQuery(
    {
        queryKey:["posts","post-uploaded-by-self",`post-${postId}`],
        queryFn: ()=>fetchPostById(postId)
    }
)
