import { useFetchPosts } from "@/api/queries/post.queries"
import PostDetailCard from "@/components/functionality/post/post-detail-card"
import PostFilter from "@/components/functionality/post/post-filter"
import { Field } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import Pageable from "@/components/ui/Pagable"
import type { TPostWisthCommentsAndLikeResponse } from "@/types/apiResponseTypes/TPostWisthCommentsAndLikeResponse.type"
import type { TPageableProps } from "@/types/propsTypes/TPageableProps.type"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { UseQueryResult } from "@tanstack/react-query"
import type { AxiosResponse } from "axios"
import { useEffect, useRef, type ReactNode } from "react"
import { useSearchParams } from "react-router-dom"
type TPostListPageProps = {
    query: ({ page, limit, query }: {
    page?: string | undefined;
    limit?: string | undefined;
    query?: string | undefined;
    postTo?:string | undefined;
    postFrom?: string | undefined;
}) => UseQueryResult<Promise<AxiosResponse<TGlobalResponse<TPageableProps<TPostWisthCommentsAndLikeResponse>>, any, {}>>>
}
const PostListPage = ({ query }: TPostListPageProps) => {
    const [searchParams] = useSearchParams()
    const { isLoading, data } = query({
        page: searchParams.get("page") ?? undefined,
        limit: searchParams.get("limit") ?? undefined,
        query: searchParams.get("query") ?? undefined,
        postTo: searchParams.get("postTo") ?? undefined,
        postFrom: searchParams.get("postFrom") ?? undefined,
    })
    useEffect(() => {
        console.log(data, isLoading)
    }, [data, isLoading])
    
    return (
        <>
            <div className="flex flex-col gap-2 w-1/1 my-2">
                <PostFilter />
                {isLoading ? <>Fetching posts</> : <div className="flex flex-col gap-2 w-1/1 mx-2 md:mx-auto md:w-2/5">
                    {data && <Pageable data={data?.data} render={(post: TPostWisthCommentsAndLikeResponse): ReactNode => <PostDetailCard post={post} key={`post-${post.id}`} />} />}
                </div>}
            </div>
        </>
    )
}

export default PostListPage