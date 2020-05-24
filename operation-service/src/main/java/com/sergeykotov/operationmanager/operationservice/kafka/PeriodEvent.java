package com.sergeykotov.operationmanager.operationservice.kafka;

public enum PeriodEvent {
    PERIOD_CREATED("period %s has been created"),
    PERIOD_NOT_CREATED("failed to create period %s"),
    PERIOD_UPDATED("period %s has been updated"),
    PERIOD_NOT_UPDATED("failed to update period %s"),
    PERIOD_DELETED("period has been deleted by ID %d"),
    PERIOD_NOT_DELETED("failed to delete period by ID %d");

    private final String message;

    PeriodEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}