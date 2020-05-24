package com.sergeykotov.operationmanager.reportservice.repository;

import com.sergeykotov.operationmanager.reportservice.model.Op;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Op, Long> {
}