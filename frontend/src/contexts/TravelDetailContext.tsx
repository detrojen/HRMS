import type { TTravelDetails } from "@/types/apiResponseTypes/TTravelDetails.type";
import { createContext } from "react";

export const TravelDetailContext = createContext<TTravelDetails>(null!)