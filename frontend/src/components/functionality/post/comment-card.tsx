import { Card, CardContent, CardDescription } from "@/components/ui/card"
import EmployeeMinDetailCard from "../employee-min-detail-card"
import type { TCommentResponse } from "@/types/apiResponseTypes/TCommentResponse.type"
import { Item, ItemContent, ItemDescription, ItemTitle } from "@/components/ui/item"

const CommentCard = ({comment}:{comment:TCommentResponse})=> {
    return (
        <Item>
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