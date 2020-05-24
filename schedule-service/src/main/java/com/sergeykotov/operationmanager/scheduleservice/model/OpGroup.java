package com.sergeykotov.operationmanager.scheduleservice.model;

import java.util.Objects;

public class OpGroup {
    private long id;

    public OpGroup(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpGroup opGroup = (OpGroup) o;
        return getId() == opGroup.getId();
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