import type { TGameType } from "@/types/apiResponseTypes/TGameType.type";
import { Field, FieldGroup, FieldLabel } from "../../ui/field";
import { Input } from "../../ui/input";
import { Checkbox } from "../../ui/checkbox";
import { Button } from "../../ui/button";
import { useForm } from "react-hook-form";
import { Form, FormControl, FormField, FormItem } from "../../ui/form";
import useCreategameTypeMutation from "@/api/mutations/create-game-type.mutation";
import useUpdategameTypeMutation from "@/api/mutations/update-game-type.mutation";
import { useContext } from "react";
import { AuthContext } from "@/contexts/AuthContextProvider";
import { toast } from "sonner";
import { zodResolver } from "@hookform/resolvers/zod";
import { gameTypeSchema } from "@/validation-schema/game-type-schema";

const GameTypeForm = ({ gameType , isEditable=true}: { gameType: TGameType | null, isEditable:boolean }) => {
    const form = useForm<TGameType>({
        defaultValues: gameType??{
            
        }
    })
    const {user} = useContext(AuthContext)
    const createGameTypeMutation = useCreategameTypeMutation()
    const updateGameTypeMutation = useUpdategameTypeMutation()
    
    const handleSubmit =  form.handleSubmit((values)=>{
        debugger
        if(user.role != "HR"){
            toast("You can not create or update game type")
            return
        }
        if(gameType){
            updateGameTypeMutation.mutate(values)
        }else{
            createGameTypeMutation.mutate(values)
        }
    })
    
    return (
        <Form {...form} >
            
            <form onSubmit={handleSubmit}>
                <FieldGroup>
                    
                    <FormField
                        disabled={!isEditable}
                        control={form.control}
                        name="game"
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Game name</FieldLabel>
                                <FormControl>
                                <Input {...field} placeholder="enter game name"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />
                    <FormField
                        disabled={!isEditable}
                        control={form.control}
                        name="slotCanBeBookedBefore"
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Slot can be booked before</FieldLabel>
                                <FormControl>
                                <Input type="number" {...field} placeholder="enter time in minutes"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />
                    <div className="grid grid-cols-2 gap-2">
                        <FormField
                         disabled={!isEditable}
                        control={form.control}
                        name="slotDuration"
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Slot Duration</FieldLabel>
                                <FormControl>
                                <Input {...field} placeholder="slot duration in minutes"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />
                        <FormField
                        control={form.control}
                        name="maxNoOfPlayers"
                         disabled={!isEditable}
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Max no of players</FieldLabel>
                                <FormControl>
                                <Input {...field} placeholder="enter max No Of Players"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />
                        <FormField
                        control={form.control}
                        name="openingHours"
                         disabled={!isEditable}
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Opening hours</FieldLabel>
                                <FormControl>
                                <Input type="time" {...field} placeholder="enter game name"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />
                       <FormField
                        control={form.control}
                        name="closingHours"
                         disabled={!isEditable}
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Closing hours</FieldLabel>
                                <FormControl>
                                <Input type="time" {...field} placeholder="enter game name"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />

                        <FormField
                        control={form.control}
                        name="maxSlotPerDay"
                         disabled={!isEditable}
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Max slot per day</FieldLabel>
                                <FormControl>
                                <Input {...field} placeholder="enter max slot per day"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />
                        <FormField
                        control={form.control}
                        name="maxActiveSlotPerDay"
                         disabled={!isEditable}
                        render={({ field }) =>
                            <FormItem>
                                <FieldLabel>Max active slot per day</FieldLabel>
                                <FormControl>
                                <Input {...field} placeholder="enter max active slot per day"></Input>
                                </FormControl>
                            </FormItem>
                        }
                    />
                    </div>
                    <FormField
                        control={form.control}
                        name="inMaintenance"
                         disabled={!isEditable}
                        render={({ field }) =>
                            <FormItem className="flex">
                                
                                <FormControl>
                                <Checkbox onCheckedChange={(value:boolean)=>{form.setValue("inMaintenance",value)}} ></Checkbox> 
                                </FormControl>
                                <p>is in maintenance</p>
                            </FormItem>
                        }
                    />
                </FieldGroup>
                <Button type="submit"  disabled={!isEditable}>{gameType? "update":"save"}</Button>
            </form>
        </Form>
    )
}

export default GameTypeForm