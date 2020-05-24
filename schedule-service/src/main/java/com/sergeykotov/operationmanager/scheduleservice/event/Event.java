package com.sergeykotov.operationmanager.scheduleservice.event;

public class Event {
    private String code;
    private String message;
    private String timestamp;
    private String body;

    public Event(String code, String message, String timestamp, String body) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return getTimestamp() + ": " + getMessage();
    }
}