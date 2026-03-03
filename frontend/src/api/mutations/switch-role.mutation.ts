import { useMutation, useQueryClient } from "@tanstack/react-query"
import { switchRole } from "../services/auth.service"

const useSwitchRoleMutation = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (roleId:number) => switchRole(roleId),
        onSuccess:(data)=>{
            if(data.data.status === "OK"){
                queryClient.invalidateQueries();
            }
        }
    })
}

export default useSwitchRoleMutation