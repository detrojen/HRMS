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
        path: "travels/assigned-travels",
        element:<TravelList asManager={false}/>
      },
      {
        path: "travels/team/assigned-travels",
        element:<TravelList asManager={true}/>
      },
      {
        path: "travels/:travelId",
        element:<TravelDetailPage />
      },
      // 
      {
        path:"game",
        element: <GameSchedulingPage />,
        children:[
          {
            index: true,
            element: <GameUsageReport /> 
          },
          {
            path: "slots/:gameTypeId",
            element: <GameSlotBook />
          },
          {
            path:"slots/requested/:requestedSlotId",
            element: <RequestedSlotDetail />
          },
          {
            path:"types",
            element: <GameTypeList />
          },
          {
            path:"types/:gameTypeId",
            element: <GameTypeDetail />
          },
          {
            path: "types/add",
            element: <GameTypeForm gameType={null} isEditable={false}/>
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
      <Toaster />
   
    </>
  )
}

export default App
