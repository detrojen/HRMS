import { Skeleton } from "../skeleton"

const EmployeeMinDetailSkeletonCard = () => {
    return <>
        <div className="relative flex gap-1 content-center w-1/1">
            <Skeleton className=" h-8 w-8 rounded-4xl my-auto">
                {/* <Skeleton className="rounded-lg"></Skeleton> */}
            </Skeleton>
            <div>
                <Skeleton className="text-xs w-1/1 min-w-50 max-h-10 my-1 text-transparent"><span>.</span></Skeleton>
                <Skeleton className="text-xs w-1/1 min-w-40 max-h-10 my-1 text-transparent" >.</Skeleton>
            </div>
        </div>
    </>
}

export default EmployeeMinDetailSkeletonCard