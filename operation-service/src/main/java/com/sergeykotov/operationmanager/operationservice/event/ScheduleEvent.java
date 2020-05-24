package com.sergeykotov.operationmanager.operationservice.event;

import com.sergeykotov.operationmanager.operationservice.model.Op;

import java.util.List;

public class ScheduleEvent {
    private long opGroupId;
    private List<Op> schedule;

    public ScheduleEvent(long opGroupId, List<Op> schedule) {
        this.opGroupId = opGroupId;
        this.schedule = schedule;
    }

    public long getOpGroupId() {
        return opGroupId;
    }

    public List<Op> getSchedule() {
        return schedule;
    }
}