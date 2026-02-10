import Layout from './Layout'
import { createBrowserRouter, RouterProvider, type RouteObject } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import HomePage from './pages/HomePage'
import GameSchedulingPage from './pages/GameSchedulingPage'
import GameUsageReport from './components/functionality/game-usage-report'
import GameSlotList from './components/functionality/game-slot-list'
import { Toaster } from 'sonner'
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
        path:"game-slots",
        element: <GameSchedulingPage />,
        children:[
          {
            index: true,
            element: <GameUsageReport /> 
          },
          {
            path: ":gameTypeId",
            element: <GameSlotList />
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
