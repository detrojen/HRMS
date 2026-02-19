import { useFetchPosts } from "@/api/queries/post.queries"
import PostDetailCard from "@/components/functionality/post/post-detail-card"
import PostFilter from "@/components/functionality/post/post-filter"
import { Field } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import Pageable from "@/components/ui/Pagable"
import type { TPostWisthCommentsAndLikeResponse } from "@/types/apiResponseTypes/TPostWisthCommentsAndLikeResponse.type"
import { useRef, type ReactNode } from "react"
import { useSearchParams } from "react-router-dom"

const PostListPage = () => {
    const [searchParams, setSearchParams] = useSearchParams()
    const { isLoading, data } = useFetchPosts({
        page: searchParams.get("page") ?? undefined,
        limit: searchParams.get("limit") ?? undefined,
        query: searchParams.get("query") ?? undefined,

    })
    const debouceRef = useRef<number|null>(null)
    const handleInputChage = (value:string) => {
        if(debouceRef != null){
            clearTimeout(debouceRef.current!)
        }
        debouceRef.current = setTimeout(()=>{
            searchParams.set("query",value)
            setSearchParams(searchParams)
        },500)
    }
    return (
        <>
            <div className="flex flex-col gap-2 w-1/1 my-2">
            <PostFilter />
                {/* <Field className="w-1/1 mx-2 md:mx-auto md:w-2/5">
                    <Input placeholder="Enter query" onChange={(e)=>{
                        handleInputChage(e.target.value)
                    }}/>
                </Field> */}
                <div className="flex flex-col gap-2 w-1/1 mx-2 md:mx-auto md:w-2/5">
                   {data&& <Pageable data={data?.data} render={(post: TPostWisthCommentsAndLikeResponse): ReactNode => <PostDetailCard post={post} key={`post-${post.id}`} />} />}

                </div>
            </div>
        </>
    )
}

export default PostListPage