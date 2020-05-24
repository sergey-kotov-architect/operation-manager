package com.sergeykotov.operationmanager.operationservice.event;

public enum OpGroupEvent {
    OP_GROUP_CREATED("operation group %s has been created"),
    OP_GROUP_NOT_CREATED("failed to create operation group %s"),
    OP_GROUP_UPDATED("operation group %s has been updated"),
    OP_GROUP_NOT_UPDATED("failed to update operation group %s"),
    OP_GROUP_DELETED("operation group has been deleted by ID %d"),
    OP_GROUP_NOT_DELETED("failed to delete operation group by ID %d");

    private final String message;

    OpGroupEvent(String message) {
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