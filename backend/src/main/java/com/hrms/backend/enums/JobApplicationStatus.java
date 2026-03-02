package com.hrms.backend.enums;

public enum JobApplicationStatus {
    PENDING("pending")
    ,IN_REVIEW("in review");
    private String status;

    JobApplicationStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
