import useAddEmployeeToTravelMutation from "@/api/mutations/add-employee-to-travel.mutation"
import { useGetchEmployeesByNameLike } from "@/api/queries/employee.queries";
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input";
import { Separator } from "@/components/ui/separator";
import type { TEmployeeWithNameOnly } from "@/types/TEmployeeWithNameOnly.type";
import { PlusCircle } from "lucide-react";
import { useEffect, useState } from "react";
import { useOutletContext } from "react-router-dom";

const AddEmployeeToTravelAction = ({travelId}:{travelId:number}) => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const { setIsLoading } = useOutletContext()
    const addEmployeeToTravelMutation = useAddEmployeeToTravelMutation()
    const [nameQuery, setNameQuery] = useState("")
    const { data: employeesData } = useGetchEmployeesByNameLike(nameQuery);
    const [employees, setEmployees] = useState<TEmployeeWithNameOnly[]>([])
    useEffect(()=>{
        setIsLoading(addEmployeeToTravelMutation.isPending)
        if(addEmployeeToTravelMutation.isSuccess){
            setIsOpen(false)
        }
    },[addEmployeeToTravelMutation.isPending, addEmployeeToTravelMutation.isSuccess])
    useEffect(()=>{
        setEmployees([])
    },[isOpen])
    return (
        <Dialog open={isOpen} onOpenChange={() => { setIsOpen(!isOpen) }}>
            <DialogTrigger className="flex gap-0.5"><PlusCircle /></DialogTrigger>

            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Assign travel to new employee</DialogTitle>
                </DialogHeader>
                <Input
                    id="search-player"
                    type="text"
                    placeholder="search employee"
                    onChange={(e) => { setNameQuery(e.target.value) }}
                />

                {
                    employeesData?.map(e => <h1 onClick={() => { setNameQuery(""); setEmployees(employees => [...employees, e]); }}>{e.firstName}</h1>)
                }
                <Separator></Separator>
                {
                    employees.map(employee => <div key={`reviewer-${employee.id}`} className="flex gap-5">
                        <h1>{employee.firstName} </h1>
                        <Button size={"xs"} onClick={() => {
                            setEmployees(employees.filter(reviewer => reviewer.id != employee.id))
                        }}>remove</Button>
                    </div>
                    )
                }

                <Button className="mt-2" onClick={() => {
                    addEmployeeToTravelMutation.mutate({
                        travelId,
                        employeeIds: employees.map(employee=>employee.id)
                    })
                }}>Save</Button>

            </DialogContent>
        </Dialog>
    )
}

export default AddEmployeeToTravelAction