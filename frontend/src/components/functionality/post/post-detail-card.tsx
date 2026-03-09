import { Card, CardContent, CardDescription, CardHeader } from "@/components/ui/card"
import type { TPostWisthCommentsAndLikeResponse } from "@/types/apiResponseTypes/TPostWisthCommentsAndLikeResponse.type"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import { Separator } from "@/components/ui/separator"
import { Edit, Heart, Trash } from "lucide-react"
import CommentAction from "./comment-action"
import CommentCard from "./comment-card"
import { useContext, useEffect, useState } from "react"
import { AuthContext } from "@/contexts/AuthContextProvider"
import DeleteUnappropriateContentAction from "./delete-unappropriate-content-action"
import useDeleteUnappropriatePostMutation from "@/api/mutations/delete-unappropriate-post.mutation"
import { Badge } from "@/components/ui/badge"
import usePostLikeUnlikeMutation from "@/api/mutations/post-like-unlike.mutation"
import useDeletePostMutation from "@/api/mutations/delete-post.mutation"
import useCommentMutation from "@/api/mutations/comment-mutation"
import { Link } from "react-router-dom"
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"

const PostDetailCard = ({ post }: { post: TPostWisthCommentsAndLikeResponse }) => {
    const { user } = useContext(AuthContext);
    const postLikeUnlikeMutation = usePostLikeUnlikeMutation()
    const deletePostMutation = useDeletePostMutation()
    const [isLiked, setIsLiked] = useState<boolean>(false);
    useEffect(() => {

        if (postLikeUnlikeMutation.isSuccess && postLikeUnlikeMutation.data.data.status === "OK") {
            setIsLiked(!isLiked)
        }
    }, [postLikeUnlikeMutation.isSuccess])
    return (
        <Card className="w-1/1 mx-2 md:mx-auto">
            <CardHeader className="flex justify-between">
                <div>
                    {post.createdBy ? <EmployeeMinDetailCard {...post.createdBy} /> : "System genrated"}
                    <h1>{post.title}</h1>
                </div>
                <div>
                    <div className="flex gap-1">

                        {post.createdBy && user.id === post.createdBy.id && <>
                            <Tooltip>
                                <TooltipTrigger>
                                    <Link to={"/posts/update/" + post.id}>
                                        <Edit />
                                    </Link>
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>Update post</p>
                                </TooltipContent>
                            </Tooltip>
                            <Tooltip>
                                <TooltipTrigger>
                                    <Trash onClick={() => { deletePostMutation.mutate(post.id) }} />
                                </TooltipTrigger>
                                <TooltipContent>
                                    <p>Delete post</p>
                                </TooltipContent>
                            </Tooltip>
                        </>}
                        {user.role === "HR" && <DeleteUnappropriateContentAction contentId={post.id} deleteMutation={useDeleteUnappropriatePostMutation} />}

                    </div>
                </div>
            </CardHeader>
            <CardContent className="flex flex-col gap-1">
                <img src={`/api/resource/posts/${post.attachmentPath}`} />
                <CardDescription className="my-2">
                    {post.body}
                    <Separator />
                    <div className="flex flex-wrap my-2 gap-1">
                        {post.tags.split(",").map(tag => (
                            <Badge >{tag}</Badge>
                        ))}
                    </div>
                </CardDescription>
                <Separator />
                <div className="flex justify-between">
                    <div>
                        <div className="flex gap-1"><Heart className={`${isLiked ? "text-red-500" : ""}`} onClick={() => { postLikeUnlikeMutation.mutate(post.id) }} /> {post.likeCount}</div>
                        <div className="flex relative mt-2">
                            {post.recentLikedBy.map((likedBy, idx) => (
                                <Tooltip>
                                    <TooltipTrigger>
                                        <Avatar key={likedBy.id} className={` h-8 w-8  border border-black rounded-4xl my-auto relative right-${idx * 2}`}>
                                            <AvatarFallback className="rounded-lg hover:bg-blue-300 hover:text-white">{likedBy.firstName[0] + likedBy.lastName[0]}</AvatarFallback>
                                        </Avatar>
                                    </TooltipTrigger>
                                    <TooltipContent>
                                        <p>{likedBy.firstName + " " + likedBy.lastName}</p>
                                    </TooltipContent>
                                </Tooltip>

                            ))}
                        </div>
                    </div>
                    <div>
                        <div className="flex gap-1"><CommentAction mutation={useCommentMutation} postId={post.id} /> {post.commentCount}</div>
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

