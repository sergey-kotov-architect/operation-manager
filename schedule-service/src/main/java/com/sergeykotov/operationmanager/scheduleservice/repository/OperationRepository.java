package com.sergeykotov.operationmanager.scheduleservice.repository;

import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Op, Long> {
}