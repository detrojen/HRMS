import { SidebarProvider } from "@/components/ui/sidebar"
import { Button } from "@/components/ui/button"
// import { AppSidebar } from "./AppSidebar"
import { Outlet } from "react-router-dom"
import { AppSidebar } from "@/Layout/component/sidebar/app-sidebar"
import AuthContextProvider from "@/contexts/AuthContextProvider"

const Layout = () => {
    return (
        <AuthContextProvider>
            <SidebarProvider>
                <AppSidebar />
                <Outlet />
            </SidebarProvider>
        </AuthContextProvider>
    )
}

export default Layout