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
        try {
            operationRepository.save(op);
        } catch (Exception e) {
            log.error("failed to create op {}", op, e);
            return;
        }
        log.info("op {} has been created", op);
    }

    public void update(Op op) {
        try {
            operationRepository.save(op);
        } catch (Exception e) {
            log.error("failed to update op {}", op, e);
            return;
        }
        log.info("op {} has been update", op);
    }

    public void delete(long id) {
        try {
            operationRepository.deleteById(id);
        } catch (Exception e) {
            log.error("failed to delete op with ID {}", id, e);
            return;
        }
        log.info("op with ID {} has been deleted", id);
    }

    public void update(List<Op> ops) {
        try {
            operationRepository.saveAll(ops);
        } catch (Exception e) {
            log.error("failed to update ops", e);
            return;
        }
        log.info("ops have been updated");
    }
}