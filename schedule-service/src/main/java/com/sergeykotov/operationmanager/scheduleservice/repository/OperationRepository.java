package com.sergeykotov.operationmanager.scheduleservice.repository;

import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationRepository {
    public List<Op> getOperationsByGroupId(long opGroupId) {
        //TODO: implement OperationRepository::getOperationsByGroupId
        throw new UnsupportedOperationException();
    }
}