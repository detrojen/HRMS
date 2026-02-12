import { Card } from "@/components/ui/card";
import { Item, ItemActions, ItemContent, ItemDescription, ItemFooter, ItemHeader, ItemSeparator } from "@/components/ui/item";
import type { TJobResponse } from "@/types/apiResponseTypes/TJobResponse.type";
import { Share } from "lucide-react";
import JobShareBtn from "./job-share-button";
import JobReferBtn from "./job-refer-button";
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
                    <JobShareBtn />
                    <JobReferBtn />
                    <Link to={`/jobs/${job.id}`}>get more...</Link>
                </ItemActions>
            </ItemFooter>
        </Item>
    )
}

export default JobInfoCard