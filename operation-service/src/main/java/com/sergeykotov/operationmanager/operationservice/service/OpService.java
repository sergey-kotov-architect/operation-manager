package com.sergeykotov.operationmanager.operationservice.service;

import com.sergeykotov.operationmanager.operationservice.event.OpEvent;
import com.sergeykotov.operationmanager.operationservice.exception.DatabaseException;
import com.sergeykotov.operationmanager.operationservice.message.MessageProducer;
import com.sergeykotov.operationmanager.operationservice.model.Op;
import com.sergeykotov.operationmanager.operationservice.repository.OpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpService {
    private static final Logger log = LoggerFactory.getLogger(OpService.class);

    private final OpRepository opRepository;
    private final MessageProducer messageProducer;

    @Autowired
    public OpService(OpRepository opRepository, MessageProducer messageProducer) {
        this.opRepository = opRepository;
        this.messageProducer = messageProducer;
    }

    public void create(Op op) {
        log.info("creating operation {}", op);
        try {
            opRepository.create(op);
        } catch (Exception e) {
            log.error("failed to create operation {}, {}", op, e.getMessage());
            messageProducer.sendOpEvent(OpEvent.OP_NOT_CREATED, op);
            throw new DatabaseException(e);
        }
        log.info("operation {} has been created", op);
        messageProducer.sendOpEvent(OpEvent.OP_CREATED, op);
    }

    public void update(Op op) {
        log.info("updating operation {}", op);
        try {
            opRepository.update(op);
        } catch (Exception e) {
            log.error("failed to update operation {}, {}", op, e.getMessage());
            messageProducer.sendOpEvent(OpEvent.OP_NOT_UPDATED, op);
            throw new DatabaseException(e);
        }
        log.info("operation {} has been updated", op);
        messageProducer.sendOpEvent(OpEvent.OP_UPDATED, op);
    }

    public void delete(long id) {
        log.info("deleting operation by ID {}", id);
        try {
            opRepository.delete(id);
        } catch (Exception e) {
            log.error("failed to delete operation by ID {}, {}", id, e.getMessage());
            messageProducer.sendOpEvent(OpEvent.OP_NOT_DELETED, id);
            throw new DatabaseException(e);
        }
        log.info("operation has been deleted by ID {}", id);
        messageProducer.sendOpEvent(OpEvent.OP_DELETED, id);
    }

    public void update(long opGroupId, List<Op> ops) {
        log.info("updating operations for group ID {}", opGroupId);
        try {
            opRepository.update(ops);
        } catch (Exception e) {
            log.error("failed to update operations for group ID {}", opGroupId, e);
            messageProducer.sendOpEvent(OpEvent.OPS_NOT_UPDATED, opGroupId, ops);
        }
        log.info("operations have been updated for group ID {}", opGroupId);
        messageProducer.sendOpEvent(OpEvent.OPS_UPDATED, opGroupId, ops);
    }
}