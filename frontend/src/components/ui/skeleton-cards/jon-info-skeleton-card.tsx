import { Item, ItemActions, ItemContent, ItemDescription, ItemFooter, ItemHeader, ItemSeparator } from "../item"
import { Skeleton } from "../skeleton"
import SkeletonList from "../SkeletonList"

const JonInfoSkeletonCard = () => {
    return (
        <>
            <Item variant={"outline"}>
            <ItemHeader className="h-10">
                <Skeleton className="h-8 min-w-50"/>
                <Skeleton className="w-20 h-8 rounded-2xl" />
            </ItemHeader>
            <ItemContent>
                <ItemDescription>
                    <Skeleton className="h-15 min-w-250" />
                </ItemDescription>
                <Skeleton className="min-w-100"/>
                <SkeletonList className="flex flex-wrap gap-1" items={5} render={()=><Skeleton className="w-20 h-8 rounded-2xl" />} />
                <ItemSeparator/>
            </ItemContent>
            <ItemFooter>
                <ItemActions>
                    <Skeleton className="w-20 h-8 rounded " />
                    <Skeleton className="w-20 h-8 rounded " />
                    <Skeleton className="w-20 h-8 rounded " />
                </ItemActions>
            </ItemFooter>
        </Item>
        </>
    )
}


export default JonInfoSkeletonCard