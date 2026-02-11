import type { TGameInterest } from "@/types/apiResponseTypes/TGameInterest.type";
import {  createSlice, type PayloadAction } from "@reduxjs/toolkit";

const initialState : TGameInterest[] = []

const gameIntrestSlice = createSlice(
    {
        name: "game-interest",
        initialState,
        reducers:{
            setInitialData: (state, payload:PayloadAction<TGameInterest[]>)=>{
                return payload.payload
            }
        }
    }
)
const gameIntresResucer = gameIntrestSlice.reducer;
export const {setInitialData} = gameIntrestSlice.actions;
export default gameIntresResucer