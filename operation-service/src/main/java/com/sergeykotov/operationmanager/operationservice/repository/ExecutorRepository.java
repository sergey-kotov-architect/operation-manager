package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.Executor;
import org.springframework.stereotype.Repository;

@Repository
public class ExecutorRepository {
    public void create(Executor executor) {
        throw new UnsupportedOperationException();
    }

    public void update(Executor executor) {
        throw new UnsupportedOperationException();
    }

    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}