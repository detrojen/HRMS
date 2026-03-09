import { Eye } from "lucide-react"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "./dialog"
import { useEffect, useState } from "react"

const DocRenderer = ({ url }: { url: string }) => {
    const [data, setData] = useState("")
    useEffect(() => {
        fetch(url)
            .then(async res => {
                const blob = await res.blob()
                const objectUrl = URL.createObjectURL(blob)
                setData(objectUrl)
            })
    }, [])
    if (url.includes(".pdf")) {
        return <object
            type="application/pdf"
            data={data}
            className="min-h-500 h-1/1 w-1/1"
            >
            fallback
        </object>
    } else if (url.includes(".png") || url.includes(".jpg")) {
        return <img src={data} width="800"
            height="500" />
    }
    return <>provided file url can not be rendered</>
}

const DocViewer = ({ url }: { url: string }) => {
    return (
        <Dialog>
            <DialogTrigger><Eye className="text-blue-500" /></DialogTrigger>

            <DialogContent className="w-9/10 h-9/10">
                <DialogHeader>
                    <DialogTitle>Documnet</DialogTitle>
                    <DocRenderer url={url} />
                </DialogHeader>
            </DialogContent>
        </Dialog>
    )
}

export default DocViewer
export { DocRenderer }