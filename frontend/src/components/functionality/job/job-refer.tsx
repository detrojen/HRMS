import useReferJobMutation from "@/api/mutations/refer-job-mutation"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Field, FieldLabel } from "@/components/ui/field"
import { FormField } from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import type { TReferJobRequest } from "@/types/apiRequestTypes/TReferJobRequest.type"
import { Repeat2 } from "lucide-react"
import { useState } from "react"
import { useForm } from "react-hook-form"

const JobRefer = ({jobId}:{jobId:number}) => {
    const {control, setValue, handleSubmit} = useForm<TReferJobRequest>()
    const [isOpen,setIsOpen] = useState<boolean>(false);
    const referJobMutation = useReferJobMutation()
    const submitHandler = handleSubmit((values)=>{
        referJobMutation.mutate( {jobId, ...values})
    })
    return (
        <Dialog open={isOpen} onOpenChange={()=>{setIsOpen(!isOpen)}}>
                <DialogTrigger><Repeat2></Repeat2></DialogTrigger>
                
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Share this job with your friend</DialogTitle>
                    </DialogHeader>
                    {/* <DialogDescription> */}
                            <FormField 
                                control={control}
                                name="basicDetail.applicantsName"
                                render={({field}) =>
                                    <Field>
                                        <FieldLabel>Name:</FieldLabel>
                                        <Input {...field}/>
                                    </Field>
                                }
                            />
                            <FormField 
                                control={control}
                                name="basicDetail.applicantsEmail"
                                render={({field}) =>
                                    <Field>
                                        <FieldLabel>Email:</FieldLabel>
                                        <Input {...field} placeholder="eg: abc@gmail.com"/>
                                    </Field>
                                }
                            />
                            <FormField 
                                control={control}
                                name="basicDetail.applicantsPhone"
                                render={({field}) =>
                                    <Field>
                                        <FieldLabel>Phone:</FieldLabel>
                                        <Input {...field} placeholder="+91 xxxxx xxxxx"/>
                                    </Field>
                                }
                            />
                            <FormField 
                                control={control}
                                name="basicDetail.details"
                                render={({field}) =>
                                    <Field>
                                        <FieldLabel>Details:</FieldLabel>
                                        <Textarea {...field} placeholder="details about your freind"/>
                                    </Field>
                                }
                            />
                            <FormField 
                                control={control}
                                name="cv"
                                render={({field}) =>
                                    <Field>
                                        <FieldLabel>Upload Cv:</FieldLabel>
                                        <Input type="file" onChange={(e)=>{
                                            setValue(field.name, e.target.files![0])
                                        }}/>
                                    </Field>
                                }
                            />
                            <Button onClick={()=>submitHandler()} className="mt-2">Reffer</Button>
                        {/* </DialogDescription> */}
                </DialogContent>
            </Dialog>
    )
}
export default JobRefer