package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.Op;
import org.springframework.stereotype.Repository;

@Repository
public class OpRepository {
    public void create(Op op) {
        throw new UnsupportedOperationException();
    }

    public void update(Op op) {
        throw new UnsupportedOperationException();
    }

    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}