import api from "@/api/api";
import type { TSelfResponse } from "@/types/apiResponseTypes/TSelfResponse.type";
import { createContext, useEffect, useState, type ReactNode } from "react";
import { useNavigate } from "react-router-dom";

type TAuthContext = {
    user : TSelfResponse
    logout: () => void
}
export const AuthContext = createContext<TAuthContext>(null!)

const AuthContextProvider = ({children}:{children:ReactNode}) => {
    const [user,setUser] = useState<TSelfResponse>(null!)
    const navTo=useNavigate()
    const logout = () => {
    localStorage.removeItem("HRMS_AUTH_TOKEN")
    navTo("/login")
  }
    useEffect(()=>{
        api.get("/api/employees/self").then(res=>{
            setUser(res.data)
        })
    },[])
    return (
        <>
            <AuthContext.Provider value={{user, logout}}>
                {user != null ? children : <h1>Loading</h1>}
            </AuthContext.Provider>
        </>
    )
}
export default AuthContextProvider