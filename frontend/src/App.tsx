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
const routes : RouteObject[] = [
  {
    path:"/login",
    element:<LoginPage />
  },
  {
    path: "",
    element:<Layout />,
    children: [
      {
        index: true,
        element: <HomePage />
      },
      {
        path:"jobs",
        element: <JobListPage />
      },
      {
        path:"jobs/:jobId",
        element:<JobDetailPage />
      },
      {
        path:"jobs/add",
        element:<JobForm />
      },
      {
        path: "travels/add",
        element:<TravelForm />
      },
      {
        path:"travels/manage",
        element:<TravelList getAsa='hr' />
      },
      {
        path: "travels/assigned-travels",
        element:<TravelList getAsa='assigned'/>
      },
      {
        path: "travels/team/assigned-travels",
        element:<TravelList getAsa='as-a-manager'/>
      },
      {
        path: "travels/:travelId",
        element:<TravelDetailPage />
      },
      {
        path:"posts",
        element: <PostListPage />
      },
      {
        path:"posts/create",
        element: <CreatePostPage />
      },
      {
        path:"org-chart",
        element: <OrgChartPage />
      },
      
      // 
      {
        path:"game",
        element: <GameSchedulingPage />,
        children:[
          {
            path: "game/usage",
            element: <GameUsageReport /> 
          },
          {
            path: "book-slot",
            element: <GameSlotBook />
          },
          {
            path:"slots/requested/:requestedSlotId",
            element: <RequestedSlotDetail />
          },
          {
            index:true,
            element: <GameTypeList />
          },
          {
            path:"types/:gameTypeId",
            element: <GameTypeDetail />
          },
          {
            path: "types/add",
            element: <GameTypeForm gameType={null} isEditable={true}/>
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
      
      <RouterProvider router={router}/>
      <Toaster visibleToasts={10}/>
   
    </>
  )
}

export default App
