package com.sergeykotov.operationmanager.operationservice.kafka;

public enum ExecutorEvent {
    EXECUTOR_CREATED("executor %s has been created"),
    EXECUTOR_NOT_CREATED("failed to create executor %s"),
    EXECUTOR_UPDATED("executor %s has been updated"),
    EXECUTOR_NOT_UPDATED("failed to update executor %s"),
    EXECUTOR_DELETED("executor has been deleted by ID %d"),
    EXECUTOR_NOT_DELETED("failed to delete executor by ID %d");

    private final String message;

    ExecutorEvent(String message) {
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