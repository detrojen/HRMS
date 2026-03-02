import PostDetailCard from "@/components/functionality/post/post-detail-card"
import PostFilter from "@/components/functionality/post/post-filter"
import Pageable from "@/components/ui/Pagable"
import type { TPageableResponse } from "@/types/apiResponseTypes/TPageableResponse.type"
import type { TPostWisthCommentsAndLikeResponse } from "@/types/apiResponseTypes/TPostWisthCommentsAndLikeResponse.type"
import type { TGlobalResponse } from "@/types/TGlobalResponse.type"
import type { UseQueryResult } from "@tanstack/react-query"
import type { AxiosResponse } from "axios"
import {  type ReactNode } from "react"
import { useSearchParams } from "react-router-dom"
type TPostListPageProps = {
    query: ({ page, limit, query, postTo, postFrom }: {
    page?: string | undefined;
    limit?: string | undefined;
    query?: string | undefined;
    postTo?: string | undefined;
    postFrom?: string | undefined;
}) => UseQueryResult<AxiosResponse<TGlobalResponse<TPageableResponse<TPostWisthCommentsAndLikeResponse>>, any, {}>, Error>
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
    const posts= data?.data.data
    
    return (
        <>
            <div className="flex flex-col gap-2 w-1/1 my-2 p-2">
                <PostFilter />
                {isLoading ? <>Fetching posts</> : <div className="flex flex-col gap-2 w-1/1">
                    {posts && <Pageable className="grid grid-cols-1 md:grid-cols-3 gap-2" data={posts} render={(post: TPostWisthCommentsAndLikeResponse): ReactNode => <PostDetailCard post={post} key={`post-${post.id}`} />} />}
                </div>}
            </div>
        </>
    )
}

export default PostListPage