"use client"

import * as React from "react"
import {
  AudioWaveform,
  BookOpen,
  Bot,
  Command,
  Frame,
  GalleryVerticalEnd,
  Gamepad2,
  HomeIcon,
  Map,
  PieChart,
  Settings2,
  SquareTerminal,
} from "lucide-react"

import { NavMain } from "@/Layout/component/sidebar/nav-main"
import { NavProjects } from "@/Layout/component/sidebar/nav-projects"
import { NavUser } from "@/Layout/component/sidebar/nav-user"
import { TeamSwitcher } from "@/Layout/component/sidebar/team-switcher"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar"
import { AuthContext } from "@/contexts/AuthContextProvider"

// This is sample data.
const data = {
  user: {
    name: "shadcn",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  teams: [
    {
      name: "Acme Inc",
      logo: GalleryVerticalEnd,
      plan: "Enterprise",
    },
    {
      name: "Acme Corp.",
      logo: AudioWaveform,
      plan: "Startup",
    },
    {
      name: "Evil Corp.",
      logo: Command,
      plan: "Free",
    },
  ],
  navMain: [
    {
      title: "Home",
      url:"/",
      icon: HomeIcon,
    },
    {
      title: "Games",
      url: "/game",
      icon: Gamepad2,
    },
    {
      title: "Game Types",
      url: "/game/types",
      icon: Gamepad2,
    },
    {
      title: "jobs",
      url: "/jobs",
      icon: Gamepad2,
    },
    {
      title: "Create Job Opening",
      url: "/jobs/add",
      icon: Gamepad2,
    },
    {
      title: "Travel",
      // url: "/travels/add",
      icon: Gamepad2,
      items: [
        {
          title: "Create travel",
          url: "/travels/add",
          role: "HR"
        },
        {
          title: "Manage Travels",
          url: "/travels/add",
          role: "HR"
        },
        {
          title: "Assigned Travels",
          url: "/travels/assigned-travels",
        },
        {
          title: "Team members Travels",
          url: "/travels/team/assigned-travels",
        },
      ]
    },
    {
      title: "login",
      url: "/login",
      icon: Gamepad2,
    }
  ],
 
}

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const {user} = React.useContext(AuthContext)
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={{
          name: user.firstName + " " +user.lastName,
          email: "",
          avatar: ""
        }} />
        
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  )
}
