import { Card } from "@/components/ui/card";
import { Item, ItemActions, ItemContent, ItemDescription, ItemFooter, ItemHeader, ItemSeparator } from "@/components/ui/item";
import type { TJobResponse } from "@/types/apiResponseTypes/TJobResponse.type";
import { Share } from "lucide-react";
import JobShare from "./job-share";
import JobRefer from "./job-refer";
import { Link } from "react-router-dom";

const JobInfoCard = ({job}:{job:TJobResponse}) => {
    return(
        <Item variant={"outline"}>
            <ItemHeader>{job.title}</ItemHeader>
            <ItemContent>
                <ItemDescription>
                    {job.description}
                </ItemDescription>
                <ItemSeparator/>
            </ItemContent>
            <ItemFooter>
                <ItemActions>
                    <JobShare jobId={job.id}/>
                    <JobRefer jobId={job.id}/>
                    <Link to={`/jobs/${job.id}`}>get more...</Link>
                </ItemActions>
            </ItemFooter>
        </Item>
    )
}


export default JobInfoCard