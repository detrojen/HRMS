import { useFetchTravelById } from "@/api/queries/travel.queries";
import type { TTravelDetails } from "@/types/apiResponseTypes/TTravelDetails.type";
import { createContext, type PropsWithChildren } from "react";
import { useParams } from "react-router-dom";

export const TravelDetailContext = createContext<TTravelDetails>(null!)