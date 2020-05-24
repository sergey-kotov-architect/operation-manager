package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.Task;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {
    public void create(Task task) {
        throw new UnsupportedOperationException();
    }

    public void update(Task task) {
        throw new UnsupportedOperationException();
    }

    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}