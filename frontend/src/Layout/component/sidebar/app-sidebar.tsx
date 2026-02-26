"use client"

import * as React from "react"
import {
  AudioWaveform,
  BookOpen,
  Bot,
  ChartBar,
  Command,
  Computer,
  Frame,
  GalleryVerticalEnd,
  Gamepad2,
  HomeIcon,
  Image,
  Map,
  PieChart,
  Plane,
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
      url: "/",
      icon: HomeIcon,
    },{
      title:"Org chart",
      url: "/org-chart",
      icon: ChartBar
    },
    {
      title: "Games",
      url: "/game",
      icon: Gamepad2,
      items: [
        {
          title: "Games",
          url: "/game",
        },
        {
          title: "Game Types",
          url: "/game/types",
        },
        {
          title: "Add game",
          url: "/game/types/add",
          role:"HR"
        },
        {
          title: "Book slot",
          url: "/game/book-slot",
        }
      ]
    },
    {
      title: "jobs",
      url: "/jobs",
      icon: Computer,
      items: [
        {
          title: "jobs",
          url: "/jobs",
          icon: Gamepad2,
        },
        {
          title: "Create Job Opening",
          url: "/jobs/add",
          icon: Gamepad2,
          role: "HR"
        },
        {
          title: "Job applications",
          url: "/jobs/job-applications",
          // role: "HR"
        },
        {
          title: "Your referels",
          url: "/jobs/refered-job-applications",
          // role: "HR"
        },
      ]
    },
    {
      title: "Travel",
      // url: "/travels/add",
      icon: Plane,
      items: [
        {
          title: "Create travel",
          url: "/travels/add",
          role: "HR"
        },
        {
          title: "Manage Travels",
          url: "/travels/manage",
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
      title: "post",
      url: "/posts",
      icon: Image,
      items: [
        {
          title: "post",
          url: "/posts",
          icon: Image,
        },
        {
          title: "Personal post",
          url: "/posts/self-uploded",
          icon: Image,
        },
        {
          title: "create post",
          url: "/posts/create",
          icon: Image,
        }
      ]
    },
  ],

}

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const { user } = React.useContext(AuthContext)
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  )
}
