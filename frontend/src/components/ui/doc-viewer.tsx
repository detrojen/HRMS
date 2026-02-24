import { Eye, View } from "lucide-react"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./dialog"
import { useEffect, useState } from "react"

const DocRenderer = ({ url }: { url: string }) => {
    
    if (url.includes(".pdf")) {
        return <object
            type="application/pdf"
            data={url}
            width="800"
            height="500">
                fallback
        </object>
    } else if (url.includes(".png") || url.includes(".jpg")) {
        return <img src={url} width="800"
            height="500" />
    }
    return <>provided file url can not be rendered</>
}

const DocViewer = ({ url }: { url: string }) => {
    return (
        <Dialog >
            <DialogTrigger><Eye /></DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Documnet</DialogTitle>
                    <DocRenderer url={url} />
                </DialogHeader>
            </DialogContent>
        </Dialog>
    )
}

export default DocViewer