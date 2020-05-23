package com.sergeykotov.operationmanager.scheduleservice.service;

import com.sergeykotov.operationmanager.scheduleservice.kafka.KafkaClient;
import com.sergeykotov.operationmanager.scheduleservice.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ScheduleService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(PROCESSOR_COUNT);

    private final OperationRepository operationRepository;
    private final OptimisationService optimisationService;
    private final KafkaClient kafkaClient;

    @Autowired
    public ScheduleService(OperationRepository operationRepository,
                           OptimisationService optimisationService,
                           KafkaClient kafkaClient) {
        this.operationRepository = operationRepository;
        this.optimisationService = optimisationService;
        this.kafkaClient = kafkaClient;
    }

    public void initiateScheduling(long opGroupId) {
        log.info("creating scheduling task for operation group ID {}", opGroupId);
        SchedulingTask task = new SchedulingTask(operationRepository, optimisationService, kafkaClient, opGroupId);
        executorService.submit(task);
        log.info("scheduling task has been submitted for operation group ID {}", opGroupId);
    }
}