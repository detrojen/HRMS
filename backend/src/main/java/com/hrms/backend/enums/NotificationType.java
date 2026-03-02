package com.hrms.backend.enums;

public enum NotificationType {
    TRAVEL("Travel")
    ,GAMES("Games")
    ,JOBS("Jobs")
    ,EXPENSES("Expenses");

    private String status;

    NotificationType(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
