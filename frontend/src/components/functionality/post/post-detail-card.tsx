import { Card, CardContent, CardDescription, CardHeader } from "@/components/ui/card"
import type { TPostWisthCommentsAndLikeResponse } from "@/types/apiResponseTypes/TPostWisthCommentsAndLikeResponse.type"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import { Separator } from "@/components/ui/separator"
import { ChartBar, Heart, Trash } from "lucide-react"
import CommentAction from "./comment-action"
import CommentCard from "./comment-card"
import { useContext } from "react"
import { AuthContext } from "@/contexts/AuthContextProvider"
import DeleteUnappropriateContentAction from "./delete-unappropriate-content-action"
import useDeleteUnappropriatePostMutation from "@/api/mutations/delete-unappropriate-post.mutation"
import { Badge } from "@/components/ui/badge"

const PostDetailCard = ({ post }: { post: TPostWisthCommentsAndLikeResponse }) => {
    const {user} = useContext(AuthContext);
    return (
        <Card className="w-1/1 mx-2 md:mx-auto">
            <CardHeader className="flex justify-between">
                <div>
                    <EmployeeMinDetailCard {...post.createdBy} />
                    <h1>{post.title}</h1>
                </div>
                {user.role === "HR" && <DeleteUnappropriateContentAction contentId={post.id} deleteMutation={useDeleteUnappropriatePostMutation}/>} 
            </CardHeader>
            <CardContent className="flex flex-col gap-1">
                <img src={`/api/resource/posts/${post.attachmentPath}`} />
                <CardDescription className="my-2">
                    {post.body}
                    <Separator/>
                   <div className="flex flex-wrap my-2 gap-1">
                     {post.tags.split(",").map(tag=>(
                        <Badge >{tag}</Badge>
                    ))}
                   </div>
                </CardDescription>
                <Separator />
                <div className="flex justify-between">
                    <div>
                        <div className="flex gap-1"><Heart /> {post.likeCount}</div>
                        <div>Liked by...</div>
                    </div>
                    <div>
                        <div className="flex gap-1"><CommentAction postId={post.id} /> {post.commentCount}</div>
                    </div>

                </div>
                <div className="mt-2">
                    {
                        post.recentComments.map(comment => (<CommentCard comment={comment} key={`comment-${comment.id}`} />))
                    }
                </div>
            </CardContent>
        </Card>
    )
}

export default PostDetailCard