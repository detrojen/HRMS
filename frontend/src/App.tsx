import Layout from './Layout'
import { createBrowserRouter, RouterProvider, type RouteObject } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import HomePage from './pages/HomePage'
import GameSchedulingPage from './pages/GameSchedulingPage'
import GameUsageReport from './components/functionality/game-scheduling/game-usage-report'

import { Toaster } from 'sonner'
import RequestedSlotDetail from './components/functionality/game-scheduling/requested-slot-detail-view'
import GameTypeList from './components/functionality/game-scheduling/game-type-list'
import GameTypeForm from './components/functionality/game-scheduling/game-type-form'
import GameTypeDetail from './components/functionality/game-scheduling/game-type-detail'
import JobForm from './components/functionality/job/job-form'
import JobListPage from './pages/JobListPage'
import JobDetailPage from './pages/JobDetailPage'
import GameSlotBook from './components/functionality/game-scheduling/game-slot-book'
import TravelForm from './pages/TravelForm'
import TravelList from './pages/TravelListPage'
import TravelDetailPage from './pages/TravelDetailPage'
import CreatePostPage from './pages/CreatePostPage'
import PostListPage from './pages/PostListPage'
import OrgChartPage from './pages/OrgChartPage'
import { fetchPosts } from './api/services/post.service'
import { useFetchPosts, usePostUploadedBySelf } from './api/queries/post.queries'
import JobApplicationListPage from './pages/JobApplicationListPage'
import GameHomePage from './pages/GameHomePage'
import ReferedJobApplicationListPage from './pages/ReferedJobApllicationPage'
import RoleCheck from './components/functionality/role-check'
import JobApplicationDetailPage from './pages/JobApplicationDetailPage'
const routes: RouteObject[] = [
  {
    path: "/login",
    element: <LoginPage />
  },
  {
    path: "",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <HomePage />
      },
      {
        path: "jobs"
        , children: [
          {
            path: "",
            element: <JobListPage />
          },
          {
            path: ":jobId",
            element: <JobDetailPage />
          },
          {
            path: "add",
            element: <RoleCheck roles={["HR"]}><JobForm /></RoleCheck>
          },
          {
            path: "job-applications",
            element: <JobApplicationListPage />
          },
          {
            path: "job-applications/:jobApplicationId",
            element: <JobApplicationDetailPage />
          },
          {
            path: "refered-job-applications",
            element: <ReferedJobApplicationListPage />
          },
          
        ]
      },

      {
        path: "travels"
        , children: [
          {
            path: "add",
            element: <RoleCheck roles={["HR"]}><TravelForm /></RoleCheck>
          },
           {
            path: "update/:travelId",
            element: <RoleCheck roles={["HR"]}><TravelForm /></RoleCheck>
          },
          {
            path: "manage",
            element: <TravelList getAsa='hr' />
          },
          {
            path: "assigned-travels",
            element: <TravelList getAsa='assigned' />
          },
          {
            path: "team/assigned-travels",
            element: <TravelList getAsa='as-a-manager' />
          },
          {
            path: ":travelId",
            element: <TravelDetailPage />
          },
        ]
      },
      {
        path: "posts",
        children: [
          {
            path: "",
            element: <PostListPage query={useFetchPosts} />
          },
          {
            path: "self-uploded",
            element: <PostListPage query={usePostUploadedBySelf} />
          },
          {
            path: "create",
            element: <CreatePostPage />
          },
          {
            path: "update/:postId",
            element: <CreatePostPage />
          },

        ]
      },

      {
        path: "org-chart",
        element: <OrgChartPage />
      },
      {
        path: "game",
        element: <GameSchedulingPage />,
        children: [
          {
            index: true,
            element: <GameHomePage />
          },
          {
            path: "game/usage",
            element: <GameUsageReport />
          },
          {
            path: "book-slot",
            element: <GameSlotBook />
          },
          {
            path: "slots/requested/:requestedSlotId",
            element: <RequestedSlotDetail />
          },
          {
            path: "types",
            element: <GameTypeList />
          },
          {
            path: "types/:gameTypeId",
            element: <GameTypeDetail />
          },
          {
            path: "types/add",
            element: <RoleCheck roles={["HR"]}><GameTypeForm gameType={null} isEditable={true} /></RoleCheck>
          }
        ]
      }
    ]
  }
]
const router = createBrowserRouter(routes)
function App() {

  return (
    <>

      <RouterProvider router={router} />
      <Toaster visibleToasts={10} />

    </>
  )
}

export default App
