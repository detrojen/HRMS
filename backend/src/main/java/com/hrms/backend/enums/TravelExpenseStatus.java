package com.hrms.backend.enums;

public enum TravelExpenseStatus {
    APPROVED("approved")
    ,REJECTED("rejected")
    ,PENDING("pending");

    private String status;

    TravelExpenseStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
