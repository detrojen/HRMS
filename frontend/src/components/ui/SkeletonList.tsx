import type { HTMLAttributes, ReactNode } from "react"
const SkeletonList = ({ items, render , className}: { items: number,render:()=>ReactNode } & HTMLAttributes<HTMLDivElement>) => {
    return (
        <>
        <div className={className}>
            {Array(items).fill(0).map((_,idx)=><div key={idx}>{render()}</div>
        )}
        </div>
        
        </>
    )
}

export default SkeletonList