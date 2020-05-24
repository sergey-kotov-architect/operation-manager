package com.sergeykotov.operationmanager.scheduleservice.event;

public enum EventType {
    SCHEDULING_INITIATED("scheduling has been initiated for operation group ID %d"),
    SCHEDULE_GENERATED("schedule has been generated for operation group ID %d");

    private final String message;

    EventType(String message) {
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