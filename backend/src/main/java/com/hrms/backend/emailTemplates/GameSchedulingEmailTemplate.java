package com.hrms.backend.emailTemplates;

import com.hrms.backend.entities.GameSchedulingEntities.SlotRequest;

public class GameSchedulingEmailTemplate {
    public static String forSlotRequest(SlotRequest slotRequest){
        return new StringBuilder()
                .append("Hello all,")
                .append("\n\nSlot request for "+ slotRequest.getGameSlot().getGameType().getGame())
                .append(" on "+ slotRequest.getGameSlot().getSlotDate())
                .append(" from " + slotRequest.getGameSlot().getStartsFrom())
                .append(" to " + slotRequest.getGameSlot().getEndsAt())
                .append(getStatusOfSlotRequest(slotRequest.getStatus()))
                .toString();
    }
    private static  String getStatusOfSlotRequest(String status){
        String s;
        switch (status){
            case "Confirm":
                return  "has been confirmed";
            case "Cancel":
                return "has been canceled";
            case "On Hold":
                return "has been on hold";
            default:
                s = "";
        }
        return s;
    }
}
