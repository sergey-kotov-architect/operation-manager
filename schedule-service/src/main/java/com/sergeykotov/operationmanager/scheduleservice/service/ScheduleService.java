package com.sergeykotov.operationmanager.scheduleservice.service;

import com.sergeykotov.operationmanager.scheduleservice.exception.DatabaseException;
import com.sergeykotov.operationmanager.scheduleservice.message.MessageProducer;
import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import com.sergeykotov.operationmanager.scheduleservice.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(PROCESSOR_COUNT);

    private final OperationRepository operationRepository;
    private final OptimisationService optimisationService;
    private final MessageProducer messageProducer;

    @Autowired
    public ScheduleService(OperationRepository operationRepository,
                           OptimisationService optimisationService,
                           MessageProducer messageProducer) {
        this.operationRepository = operationRepository;
        this.optimisationService = optimisationService;
        this.messageProducer = messageProducer;
    }

    public void createSchedulingTask(long opGroupId) {
        log.info("creating scheduling task for operation group ID {}", opGroupId);
        List<Op> allOps = new ArrayList<>();
        try {
            operationRepository.findAll().forEach(allOps::add);
        } catch (Exception e) {
            log.error("failed to extract operations from database for group ID {}", opGroupId, e);
            throw new DatabaseException(e);
        }
        List<Op> ops = allOps.stream().filter(o -> o.getOpGroup().getId() == opGroupId).collect(Collectors.toList());
        SchedulingTask task = new SchedulingTask(optimisationService, messageProducer, opGroupId, ops);
        executorService.submit(task);
        log.info("scheduling task has been submitted for operation group ID {}", opGroupId);
    }
}