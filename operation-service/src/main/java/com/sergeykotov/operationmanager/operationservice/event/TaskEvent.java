package com.sergeykotov.operationmanager.operationservice.event;

public enum TaskEvent {
    TASK_CREATED("task %s has been created"),
    TASK_NOT_CREATED("failed to create task %s"),
    TASK_UPDATED("task %s has been updated"),
    TASK_NOT_UPDATED("failed to update task %s"),
    TASK_DELETED("task has been deleted by ID %d"),
    TASK_NOT_DELETED("failed to delete task by ID %d");

    private final String message;

    TaskEvent(String message) {
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