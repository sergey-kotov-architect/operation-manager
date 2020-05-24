package com.sergeykotov.operationmanager.operationservice.kafka;

public enum OpEvent {
    OP_CREATED("operation %s has been created"),
    OP_NOT_CREATED("failed to create operation %s"),
    OP_UPDATED("operation %s has been updated"),
    OP_NOT_UPDATED("failed to update operation %s"),
    OP_DELETED("operation has been deleted by ID %d"),
    OP_NOT_DELETED("failed to delete operation by ID %d");

    private final String message;

    OpEvent(String message) {
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