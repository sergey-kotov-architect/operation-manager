package com.sergeykotov.operationmanager.scheduleservice.service;

import com.sergeykotov.operationmanager.scheduleservice.kafka.KafkaClient;
import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import com.sergeykotov.operationmanager.scheduleservice.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SchedulingTask extends Thread {
    private static final Logger log = LoggerFactory.getLogger(SchedulingTask.class);

    private final OperationRepository operationRepository;
    private final OptimisationService optimisationService;
    private final KafkaClient kafkaClient;
    private final long opGroupId;

    public SchedulingTask(OperationRepository operationRepository,
                          OptimisationService optimisationService,
                          KafkaClient kafkaClient,
                          long opGroupId) {
        this.operationRepository = operationRepository;
        this.optimisationService = optimisationService;
        this.kafkaClient = kafkaClient;
        this.opGroupId = opGroupId;
        setName("scheduling-" + opGroupId);
    }

    @Override
    public void run() {
        log.info("scheduling has been initiated for operation group ID {}", opGroupId);
        kafkaClient.sendSchedulingInitiatedEvent(opGroupId);
        List<Op> ops = operationRepository.getOperationsByGroupId(opGroupId);
        long start = System.currentTimeMillis();
        List<Op> schedule = optimisationService.generateOptimalSchedule(ops);
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        log.info("scheduling has been completed for operation group ID {} in {} milliseconds", opGroupId, elapsed);
        kafkaClient.sendGeneratedSchedule(opGroupId, start, end, elapsed, schedule);
    }
}