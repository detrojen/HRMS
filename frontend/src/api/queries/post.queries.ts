import { useQuery } from "@tanstack/react-query";
import { fetchPosts } from "../services/post.service";

export const useFetchPosts = ({page,limit,query}:{page?:string,limit?:string,query?:string}) => useQuery(
    {
        queryKey:["posts",{page,limit,query}],
        queryFn: ()=>fetchPosts({page,limit,query})
    }
)
