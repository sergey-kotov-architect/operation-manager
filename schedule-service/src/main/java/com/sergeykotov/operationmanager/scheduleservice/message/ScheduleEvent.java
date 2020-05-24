package com.sergeykotov.operationmanager.scheduleservice.message;

import com.sergeykotov.operationmanager.scheduleservice.model.Op;

import java.util.List;

public class ScheduleEvent {
    private long opGroupId;
    private long start;
    private long end;
    private long elapsed;
    private List<Op> schedule;

    public ScheduleEvent(long opGroupId, long start, long end, long elapsed, List<Op> schedule) {
        this.opGroupId = opGroupId;
        this.start = start;
        this.end = end;
        this.elapsed = elapsed;
        this.schedule = schedule;
    }

    public long getOpGroupId() {
        return opGroupId;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getElapsed() {
        return elapsed;
    }

    public List<Op> getSchedule() {
        return schedule;
    }
}