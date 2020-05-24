package com.sergeykotov.operationmanager.operationservice.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class Op {
    private long id;

    @NotEmpty
    private String name;

    private String note;

    @NotEmpty
    private String status;

    @Positive
    private double profit;

    @Positive
    private double cost;

    @NotNull
    private OpGroup opGroup;

    @NotNull
    private Task task;

    @NotNull
    private Executor executor;

    @NotNull
    private Period period;

    public Op() {
    }

    public Op(long id, String name, String note, String status, double profit, double cost, OpGroup opGroup, Task task, Executor executor, Period period) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.status = status;
        this.profit = profit;
        this.cost = cost;
        this.opGroup = opGroup;
        this.task = task;
        this.executor = executor;
        this.period = period;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public OpGroup getOpGroup() {
        return opGroup;
    }

    public void setOpGroup(OpGroup opGroup) {
        this.opGroup = opGroup;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
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
        return getName();
    }
}