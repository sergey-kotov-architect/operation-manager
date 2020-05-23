package com.sergeykotov.operationmanager.scheduleservice.model;

import java.util.Objects;

public class Op {
    private long id;
    private double cost;
    private Task task;
    private Executor executor;
    private Period period;

    public Op(long id, double cost, Task task, Executor executor, Period period) {
        this.id = id;
        this.cost = cost;
        this.task = task;
        this.executor = executor;
        this.period = period;
    }

    public long getId() {
        return id;
    }

    public double getCost() {
        return cost;
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