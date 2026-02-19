import useCreatePostMutation from "@/api/mutations/create-post.mutation"
import { Button } from "@/components/ui/button"
import { Card } from "@/components/ui/card"
import { Field, FieldLabel } from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import TagsAdd from "@/components/ui/tags-add"
import type { TCreatePostRequest } from "@/types/apiRequestTypes/TCreatePostRequest.type"
import { Controller, useForm, type ControllerRenderProps } from "react-hook-form"

const CreatePostPage = () => {
    const form = useForm<TCreatePostRequest>()
    const postMutation = useCreatePostMutation()
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>, field: ControllerRenderProps<TCreatePostRequest, "attachment">) => {
        if (e.target.files != null && e.target.files.length > 0) {
            form.setValue(field.name, e.target.files[0]);
        }
    }
    const handleSubmit = form.handleSubmit((values) => {
        debugger
        postMutation.mutate(values)
    })
    return (
        <Card className="w-1/1 md:w-2/3 mx-10 md:mx-auto p-3">
            <Controller
                control={form.control}
                name="postDetails.title"
                render={({ field, fieldstate }) => (
                    <Field>
                        <FieldLabel>Title</FieldLabel>
                        <Input {...field} />
                    </Field>
                )}
            />
            <Controller
                control={form.control}
                name="postDetails.body"
                render={({ field, fieldstate }) => (
                    <Field>
                        <FieldLabel>Details</FieldLabel>
                        <Input {...field} />
                    </Field>
                )}
            />
            <Controller
                control={form.control}
                name="postDetails.tags"
                render={({ field, fieldstate }) => (
                    <Field>
                        <FieldLabel>Tags</FieldLabel>
                        <TagsAdd form={form} field={field} placeholder="Enter tags" />
                    </Field>
                )}
            />
            <Controller
                control={form.control}
                name="attachment"
                render={({ field, fieldstate }) => (
                    <Field>
                        <FieldLabel>Add attachment</FieldLabel>
                        <Input type="file" onChange={(e) => {
                            handleFileChange(e, field)
                        }} />
                    </Field>
                )}
            />
            <Button onClick={handleSubmit}>Save</Button>
        </Card>
    )
}

export default CreatePostPage