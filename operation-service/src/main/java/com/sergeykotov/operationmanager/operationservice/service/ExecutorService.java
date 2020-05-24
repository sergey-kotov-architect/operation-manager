package com.sergeykotov.operationmanager.operationservice.service;

import com.sergeykotov.operationmanager.operationservice.exception.DatabaseException;
import com.sergeykotov.operationmanager.operationservice.event.ExecutorEvent;
import com.sergeykotov.operationmanager.operationservice.message.MessageProducer;
import com.sergeykotov.operationmanager.operationservice.model.Executor;
import com.sergeykotov.operationmanager.operationservice.repository.ExecutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutorService {
    private static final Logger log = LoggerFactory.getLogger(ExecutorService.class);

    private final ExecutorRepository executorRepository;
    private final MessageProducer messageProducer;

    @Autowired
    public ExecutorService(ExecutorRepository executorRepository, MessageProducer messageProducer) {
        this.executorRepository = executorRepository;
        this.messageProducer = messageProducer;
    }

    public void create(Executor executor) {
        log.info("creating executor {}", executor);
        try {
            executorRepository.create(executor);
        } catch (Exception e) {
            log.error("failed to create executor {}, {}", executor, e.getMessage());
            messageProducer.sendExecutorEvent(ExecutorEvent.EXECUTOR_NOT_CREATED, executor);
            throw new DatabaseException(e);
        }
        log.info("executor {} has been created", executor);
        messageProducer.sendExecutorEvent(ExecutorEvent.EXECUTOR_CREATED, executor);
    }

    public void update(Executor executor) {
        log.info("updating executor {}", executor);
        try {
            executorRepository.update(executor);
        } catch (Exception e) {
            log.error("failed to update executor {}, {}", executor, e.getMessage());
            messageProducer.sendExecutorEvent(ExecutorEvent.EXECUTOR_NOT_UPDATED, executor);
            throw new DatabaseException(e);
        }
        log.info("executor {} has been updated", executor);
        messageProducer.sendExecutorEvent(ExecutorEvent.EXECUTOR_UPDATED, executor);
    }

    public void delete(long id) {
        log.info("deleting executor by ID {}", id);
        try {
            executorRepository.delete(id);
        } catch (Exception e) {
            log.error("failed to delete executor by ID {}, {}", id, e.getMessage());
            messageProducer.sendExecutorEvent(ExecutorEvent.EXECUTOR_NOT_DELETED, id);
            throw new DatabaseException(e);
        }
        log.info("executor has been deleted by ID {}", id);
        messageProducer.sendExecutorEvent(ExecutorEvent.EXECUTOR_DELETED, id);
    }
}