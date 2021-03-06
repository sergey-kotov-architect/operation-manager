package com.sergeykotov.operationmanager.operationservice.service;

import com.sergeykotov.operationmanager.operationservice.exception.DatabaseException;
import com.sergeykotov.operationmanager.operationservice.message.MessageProducer;
import com.sergeykotov.operationmanager.operationservice.event.OpGroupEvent;
import com.sergeykotov.operationmanager.operationservice.model.OpGroup;
import com.sergeykotov.operationmanager.operationservice.repository.OpGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpGroupService {
    private static final Logger log = LoggerFactory.getLogger(OpGroupService.class);

    private final OpGroupRepository opGroupRepository;
    private final MessageProducer messageProducer;

    @Autowired
    public OpGroupService(OpGroupRepository opGroupRepository, MessageProducer messageProducer) {
        this.opGroupRepository = opGroupRepository;
        this.messageProducer = messageProducer;
    }

    public void create(OpGroup opGroup) {
        log.info("creating operation group {}", opGroup);
        try {
            opGroupRepository.create(opGroup);
        } catch (Exception e) {
            log.error("failed to create operation group {}, {}", opGroup, e.getMessage());
            messageProducer.sendOpGroupEvent(OpGroupEvent.OP_GROUP_NOT_CREATED, opGroup);
            throw new DatabaseException(e);
        }
        log.info("operation group {} has been created", opGroup);
        messageProducer.sendOpGroupEvent(OpGroupEvent.OP_GROUP_CREATED, opGroup);
    }

    public void update(OpGroup opGroup) {
        log.info("updating operation group {}", opGroup);
        try {
            opGroupRepository.update(opGroup);
        } catch (Exception e) {
            log.error("failed to update operation group {}, {}", opGroup, e.getMessage());
            messageProducer.sendOpGroupEvent(OpGroupEvent.OP_GROUP_NOT_UPDATED, opGroup);
            throw new DatabaseException(e);
        }
        log.info("operation group {} has been updated", opGroup);
        messageProducer.sendOpGroupEvent(OpGroupEvent.OP_GROUP_UPDATED, opGroup);
    }

    public void delete(long id) {
        log.info("deleting operation group by ID {}", id);
        try {
            opGroupRepository.delete(id);
        } catch (Exception e) {
            log.error("failed to delete operation group by ID {}, {}", id, e.getMessage());
            messageProducer.sendOpGroupEvent(OpGroupEvent.OP_GROUP_NOT_DELETED, id);
            throw new DatabaseException(e);
        }
        log.info("operation group has been deleted by ID {}", id);
        messageProducer.sendOpGroupEvent(OpGroupEvent.OP_GROUP_DELETED, id);
    }
}