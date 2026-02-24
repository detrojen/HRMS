import { Card } from "@/components/ui/card";
import { Item, ItemActions, ItemContent, ItemDescription, ItemFooter, ItemHeader, ItemSeparator } from "@/components/ui/item";
import type { TJobResponse } from "@/types/apiResponseTypes/TJobResponse.type";
import { Share } from "lucide-react";
import JobShare from "./job-share";
import JobRefer from "./job-refer";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";

const JobInfoCard = ({job}:{job:TJobResponse}) => {
    return(
        <Item variant={"outline"}>
            <ItemHeader><p>{job.title}</p><Badge>{job.status}</Badge></ItemHeader>
            <ItemContent>
                <ItemDescription>
                    <p>{job.description}</p>
                </ItemDescription>
                <p>vacancy : - {job.vacancy}</p>
                <div className="flex flex-wrap gap-1">
                    {job.skills.split(",").map(skill=><Badge variant={"secondary"} key={skill}>{skill}</Badge>)}
                </div>
                <ItemSeparator/>
            </ItemContent>
            <ItemFooter>
                <ItemActions>
                    <JobShare jobId={job.id}/>
                    <JobRefer jobId={job.id}/>
                    <Link to={`/jobs/${job.id}`}><Button>Details</Button></Link>
                </ItemActions>
            </ItemFooter>
        </Item>
    )
}


export default JobInfoCard