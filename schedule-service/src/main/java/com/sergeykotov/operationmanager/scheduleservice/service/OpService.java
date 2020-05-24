package com.sergeykotov.operationmanager.scheduleservice.service;

import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import com.sergeykotov.operationmanager.scheduleservice.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpService {
    private static final Logger log = LoggerFactory.getLogger(OpService.class);

    private final OperationRepository operationRepository;

    @Autowired
    public OpService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public void create(Op op) {
        operationRepository.save(op);
    }

    public void update(Op op) {
        operationRepository.save(op);
    }

    public void delete(long id) {
        operationRepository.deleteById(id);
    }

    public void update(List<Op> ops) {
        operationRepository.saveAll(ops);
    }
}