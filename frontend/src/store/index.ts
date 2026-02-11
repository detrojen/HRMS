import { configureStore } from "@reduxjs/toolkit";
import gameIntresResucer from "./slices/game-interest-slice";


export const store = configureStore(
    {
        reducer: {
            gameInterest: gameIntresResucer 
        }
    }
)

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch; 