import { Card, CardContent, CardDescription } from "@/components/ui/card"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import type { TCommentResponse } from "@/types/apiResponseTypes/TCommentResponse.type"
import { Item, ItemContent, ItemDescription, ItemHeader, ItemTitle } from "@/components/ui/item"
import DeleteUnappropriateContentAction from "./delete-unappropriate-content-action"
import useDeleteUnappropriateCommentMutation from "@/api/mutations/delete-unappropriate-comment.mutation"
import { useContext } from "react"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { Edit, Trash } from "lucide-react"
import useDeletePostCommentMutation from "@/api/mutations/delete-comment.mutation"
import CommentAction from "./comment-action"
import useUpdateCommentMutation from "@/api/mutations/update-comment-mutation"

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
                    {user.id === comment.commentedBy.id && <CommentAction mutation={useUpdateCommentMutation} icon={Edit} value={{id:comment.id, comment:comment.comment}}/>}
                    {user.id === comment.commentedBy.id && <Trash onClick={handleDelete} />}
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