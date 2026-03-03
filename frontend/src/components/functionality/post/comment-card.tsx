import EmployeeMinDetailCard from "../employee-min-detail-card"
import type { TCommentResponse } from "@/types/apiResponseTypes/TCommentResponse.type"
import { Item, ItemContent, ItemDescription, ItemHeader, ItemTitle } from "@/components/ui/item"
import DeleteUnappropriateContentAction from "./delete-unappropriate-content-action"
import useDeleteUnappropriateCommentMutation from "@/api/mutations/delete-unappropriate-comment.mutation"
import { useContext } from "react"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { Trash } from "lucide-react"
import useDeletePostCommentMutation from "@/api/mutations/delete-comment.mutation"
import CommentAction from "./comment-action"
import useUpdateCommentMutation from "@/api/mutations/update-comment-mutation"
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip"

const CommentCard = ({ comment }: { comment: TCommentResponse }) => {
    const { user } = useContext(AuthContext)
    const commentDeleteMutation = useDeletePostCommentMutation()
    const handleDelete = () => {
        const flag = confirm("sure want to delete this comment")
        if (flag) {
            commentDeleteMutation.mutate(comment.id)
        }
    }
    return (
        <Item>
            <ItemHeader>
                <div className="flex w-1/1 justify-end">
                    {user.id === comment.commentedBy.id && 
                    <Tooltip>
                            <TooltipTrigger>
                                <CommentAction mutation={useUpdateCommentMutation} value={{ id: comment.id, comment: comment.comment }} />
                            </TooltipTrigger>
                            <TooltipContent>
                                <p>Edit comment</p>
                            </TooltipContent>
                        </Tooltip>
                    }
                    {user.id === comment.commentedBy.id &&
                        <Tooltip>
                            <TooltipTrigger>
                                <Trash onClick={handleDelete} />
                            </TooltipTrigger>
                            <TooltipContent>
                                <p>Delete comment</p>
                            </TooltipContent>
                        </Tooltip>
                    }
                    {user.role === "HR" && <DeleteUnappropriateContentAction deleteMutation={useDeleteUnappropriateCommentMutation} contentId={comment.id} />}
                </div>
            </ItemHeader>
            <ItemContent>
                <ItemTitle><EmployeeMinDetailCard {...comment.commentedBy}></EmployeeMinDetailCard></ItemTitle>
                <ItemDescription>
                    {comment.comment}
                </ItemDescription>
            </ItemContent>
        </Item>

    )
}
export default CommentCard