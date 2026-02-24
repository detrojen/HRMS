import { useQuery } from "@tanstack/react-query";
import { fetchPostById, fetchPosts, fetchPostUploadedBySelf } from "../services/post.service";

export const useFetchPosts = ({page,limit,query}:{page?:string,limit?:string,query?:string}) => useQuery(
    {
        queryKey:["posts",{page,limit,query}],
        queryFn: ()=>fetchPosts({page,limit,query})
    }
)

export const usePostUploadedBySelf = ({page,limit,query}:{page?:string,limit?:string,query?:string}) => useQuery(
    {
        queryKey:["posts",{page,limit,query},"post-uploaded-by-self"],
        queryFn: ()=>fetchPostUploadedBySelf({page,limit,query})
    }
)

export const usePostById = (postId:number) => useQuery(
    {
        queryKey:["posts","post-uploaded-by-self",`post-${postId}`],
        queryFn: ()=>fetchPostById(postId)
    }
)
