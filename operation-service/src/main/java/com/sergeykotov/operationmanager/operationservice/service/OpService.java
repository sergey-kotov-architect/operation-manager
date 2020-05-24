package com.sergeykotov.operationmanager.operationservice.service;

import com.sergeykotov.operationmanager.operationservice.exception.DatabaseException;
import com.sergeykotov.operationmanager.operationservice.kafka.KafkaClient;
import com.sergeykotov.operationmanager.operationservice.kafka.OpEvent;
import com.sergeykotov.operationmanager.operationservice.model.Op;
import com.sergeykotov.operationmanager.operationservice.repository.OpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpService {
    private static final Logger log = LoggerFactory.getLogger(OpService.class);

    private final OpRepository opRepository;
    private final KafkaClient kafkaClient;

    @Autowired
    public OpService(OpRepository opRepository, KafkaClient kafkaClient) {
        this.opRepository = opRepository;
        this.kafkaClient = kafkaClient;
    }

    public void create(Op op) {
        log.info("creating operation {}", op);
        try {
            opRepository.create(op);
        } catch (Exception e) {
            log.error("failed to create operation {}, {}", op, e.getMessage());
            kafkaClient.sendOpEvent(OpEvent.OP_NOT_CREATED, op);
            throw new DatabaseException(e);
        }
        log.info("operation {} has been created", op);
        kafkaClient.sendOpEvent(OpEvent.OP_CREATED, op);
    }

    public void update(Op op) {
        log.info("updating operation {}", op);
        try {
            opRepository.update(op);
        } catch (Exception e) {
            log.error("failed to update operation {}, {}", op, e.getMessage());
            kafkaClient.sendOpEvent(OpEvent.OP_NOT_UPDATED, op);
            throw new DatabaseException(e);
        }
        log.info("operation {} has been updated", op);
        kafkaClient.sendOpEvent(OpEvent.OP_UPDATED, op);
    }

    public void delete(long id) {
        log.info("deleting operation by ID {}", id);
        try {
            opRepository.delete(id);
        } catch (Exception e) {
            log.error("failed to delete operation by ID {}, {}", id, e.getMessage());
            kafkaClient.sendOpEvent(OpEvent.OP_NOT_DELETED, id);
            throw new DatabaseException(e);
        }
        log.info("operation has been deleted by ID {}", id);
        kafkaClient.sendOpEvent(OpEvent.OP_DELETED, id);
    }
}