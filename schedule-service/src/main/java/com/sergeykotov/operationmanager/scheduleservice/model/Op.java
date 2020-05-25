package com.sergeykotov.operationmanager.scheduleservice.model;

import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@RedisHash("Op")
public class Op {
    private long id;
    private double cost;
    private Status status;
    private OpGroup opGroup;
    private Task task;
    private Executor executor;
    private Period period;

    public enum Status {UNSCHEDULED, SCHEDULED, CANCELLED, EXECUTING, COMPLETED, FAILED}

    public Op(long id, double cost, Status status, OpGroup opGroup, Task task, Executor executor, Period period) {
        this.id = id;
        this.cost = cost;
        this.status = status;
        this.opGroup = opGroup;
        this.task = task;
        this.executor = executor;
        this.period = period;
    }

    public long getId() {
        return id;
    }

    public OpGroup getOpGroup() {
        return opGroup;
    }

    public double getCost() {
        return cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public Executor getExecutor() {
        return executor;
    }

    public Period getPeriod() {
        return period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Op op = (Op) o;
        return getId() == op.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}