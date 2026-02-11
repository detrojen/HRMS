import Layout from './Layout'
import { createBrowserRouter, RouterProvider, type RouteObject } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import HomePage from './pages/HomePage'
import GameSchedulingPage from './pages/GameSchedulingPage'
import GameUsageReport from './components/functionality/game-usage-report'
import GameSlotList from './components/functionality/game-slot-list'
import { Toaster } from 'sonner'
import { Provider } from 'react-redux'
import { store } from './store'
import RequestedSlotDetail from './components/functionality/requested-slot-detail-view'
import GameTypeList from './components/functionality/game-type-list'
import GameTypeForm from './components/functionality/game-type-form'
import GameTypeDetail from './components/functionality/game-type-detail'
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
        path:"game",
        element: <GameSchedulingPage />,
        children:[
          {
            index: true,
            element: <GameUsageReport /> 
          },
          {
            path: "slots/:gameTypeId",
            element: <GameSlotList />
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
            element: <GameTypeForm gameType={null}/>
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
