import { SidebarProvider } from "@/components/ui/sidebar"
import { Button } from "@/components/ui/button"
// import { AppSidebar } from "./AppSidebar"
import { Outlet } from "react-router-dom"
import { AppSidebar } from "@/Layout/component/sidebar/app-sidebar"
import AuthContextProvider from "@/contexts/AuthContextProvider"
import { Loader } from "lucide-react"
import { useState } from "react"
import Header from "./component/header/header"

const Layout = () => {
    const [loading,setIsLoading] = useState<boolean>(false)
    return (
        <AuthContextProvider>
            <SidebarProvider>
                <AppSidebar />
                <div className=" w-screen">
                    <Header />
                    <Outlet context={{setIsLoading}}/>
                </div>
                {loading && <div className="absolute z-100 top-0 flex justify-center align-middle left-0 h-screen w-screen bg-white opacity-55">
                    <Loader />
                </div>}
            </SidebarProvider>
            
        </AuthContextProvider>
    )
}

export default Layout