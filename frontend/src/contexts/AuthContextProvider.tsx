import api from "@/api/api";
import useSwitchRoleMutation from "@/api/mutations/switch-role.mutation";
import type { TSelfResponse } from "@/types/apiResponseTypes/TSelfResponse.type";
import type { TGlobalResponse } from "@/types/TGlobalResponse.type";
import { createContext, useEffect, useState, type ReactNode } from "react";
import { useNavigate } from "react-router-dom";

type TAuthContext = {
    user: TSelfResponse
    logout: () => void
    switchRole: (roleId:number) => void
}
export const AuthContext = createContext<TAuthContext>(null!)

const AuthContextProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<TSelfResponse>(null!)
    const navTo = useNavigate()
    const switchRoleMutaion = useSwitchRoleMutation()
    const logout = () => {
        localStorage.removeItem("HRMS_AUTH_TOKEN")
        navTo("/login")
    }
    useEffect(() => {
        api.get<TGlobalResponse<TSelfResponse>>("/api/employees/self").then(res => {
            setUser(res.data.data)
        })
    }, [])
    useEffect(()=>{
        if(switchRoleMutaion.data?.data.status === "OK"){
            setUser(switchRoleMutaion.data.data.data)
        }
    },[switchRoleMutaion.data])

    const switchRole = (roleId: number) => {
        switchRoleMutaion.mutate(roleId)
    }
    return (
        <>
            <AuthContext.Provider value={{ user, logout ,switchRole}}>
                {user != null ? children : <h1>Loading</h1>}
            </AuthContext.Provider>
        </>
    )
}
export default AuthContextProvider