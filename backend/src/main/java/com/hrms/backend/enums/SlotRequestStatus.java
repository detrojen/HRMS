package com.hrms.backend.enums;

public enum SlotRequestStatus {
    CONFIRM("Confirm")
    ,ON_HOLD("On hold")
    ,CANCEL("cancel");

    private String status;

    SlotRequestStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
