import api from "@/api/api";
import type { TSelfResponse } from "@/types/apiResponseTypes/TSelfResponse.type";
import { createContext, useEffect, useState, type ReactNode } from "react";

type TAuthContext = {
    user : TSelfResponse
}
export const AuthContext = createContext<TAuthContext>(null!)

const AuthContextProvider = ({children}:{children:ReactNode}) => {
    const [user,setUser] = useState<TSelfResponse>(null!)
    useEffect(()=>{
        api.get("/api/employees/self").then(res=>{
            setUser(res.data)
        })
    },[])
    return (
        <>
            <AuthContext.Provider value={{user}}>
                {user != null ? children : <h1>Loading</h1>}
            </AuthContext.Provider>
        </>
    )
}
export default AuthContextProvider