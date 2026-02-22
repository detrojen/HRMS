import { Card, CardContent, CardDescription } from "@/components/ui/card"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import type { TCommentResponse } from "@/types/apiResponseTypes/TCommentResponse.type"
import { Item, ItemContent, ItemDescription, ItemHeader, ItemTitle } from "@/components/ui/item"
import DeleteUnappropriateContentAction from "./delete-unappropriate-content-action"
import useDeleteUnappropriateCommentMutation from "@/api/mutations/delete-unappropriate-comment.mutation"
import { useContext } from "react"
import { AuthContext } from "@/contexts/AuthContextProvider"
import { Trash } from "lucide-react"
import useDeletePostCommentMutation from "@/api/mutations/delete-comment.mutation"

const CommentCard = ({comment}:{comment:TCommentResponse})=> {
    const {user} = useContext(AuthContext)
    const commentDeleteMutation = useDeletePostCommentMutation()
    const handleDelete = () => {
        const flag =  confirm("sure want to delete this comment")
        if(flag){
            commentDeleteMutation.mutate(comment.id)
        }
    }
    return (
        <Item>
            <ItemHeader>
                {user.id === comment.commentedBy.id && <Trash onClick={handleDelete}/>}
                {user.role === "HR" && <DeleteUnappropriateContentAction deleteMutation={useDeleteUnappropriateCommentMutation} contentId={comment.id} />}
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