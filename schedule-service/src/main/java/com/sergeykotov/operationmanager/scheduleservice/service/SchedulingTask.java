package com.sergeykotov.operationmanager.scheduleservice.service;

import com.sergeykotov.operationmanager.scheduleservice.message.MessageProducer;
import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SchedulingTask extends Thread {
    private static final Logger log = LoggerFactory.getLogger(SchedulingTask.class);

    private final OptimisationService optimisationService;
    private final MessageProducer messageProducer;
    private final long opGroupId;
    private final List<Op> ops;

    public SchedulingTask(OptimisationService optimisationService,
                          MessageProducer messageProducer,
                          long opGroupId,
                          List<Op> ops) {
        this.optimisationService = optimisationService;
        this.messageProducer = messageProducer;
        this.opGroupId = opGroupId;
        this.ops = ops;
        setName("scheduling-" + opGroupId);
    }

    @Override
    public void run() {
        log.info("scheduling has been initiated for operation group ID {}", opGroupId);
        messageProducer.sendSchedulingInitiatedEvent(opGroupId);
        long start = System.currentTimeMillis();
        List<Op> schedule = optimisationService.generateOptimalSchedule(ops);
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        log.info("scheduling has been completed for operation group ID {} in {} milliseconds", opGroupId, elapsed);
        messageProducer.sendGeneratedSchedule(opGroupId, start, end, elapsed, schedule);
    }
}