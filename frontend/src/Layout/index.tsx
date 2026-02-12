import { SidebarProvider } from "@/components/ui/sidebar"
import { Button } from "@/components/ui/button"
// import { AppSidebar } from "./AppSidebar"
import { Outlet } from "react-router-dom"
import { AppSidebar } from "@/Layout/component/sidebar/app-sidebar"

const Layout = () => {
    return<SidebarProvider>
        <AppSidebar />
        <Outlet />
    </SidebarProvider>
}

export default Layout