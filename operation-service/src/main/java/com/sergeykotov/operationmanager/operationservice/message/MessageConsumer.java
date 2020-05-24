package com.sergeykotov.operationmanager.operationservice.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergeykotov.operationmanager.operationservice.event.Event;
import com.sergeykotov.operationmanager.operationservice.event.ScheduleEvent;
import com.sergeykotov.operationmanager.operationservice.model.Op;
import com.sergeykotov.operationmanager.operationservice.service.OpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final OpService opService;

    @Autowired
    public MessageConsumer(OpService opService) {
        this.opService = opService;
    }

    @KafkaListener(topics = "SCHEDULE", groupId = "SCHEDULE")
    public void listen(String message) {
        Event event = null;
        try {
            event = objectMapper.readValue(message, Event.class);
        } catch (Exception e) {
            log.info("failed to parse message: {}", message);
        }
        if (event == null) {
            log.warn("empty event arrived");
            return;
        }
        log.info("new event arrived: {}", event.toString());
        ScheduleEvent scheduleEvent;
        try {
            scheduleEvent = objectMapper.readValue(event.getBody(), ScheduleEvent.class);
        } catch (Exception e) {
            log.error("failed to parse event body", e);
            return;
        }
        if (scheduleEvent == null) {
            log.error("event body is empty");
            return;
        }
        List<Op> schedule = scheduleEvent.getSchedule();
        if (schedule == null || schedule.isEmpty()) {
            log.error("schedule is empty");
            return;
        }
        opService.update(scheduleEvent.getOpGroupId(), schedule);
    }
}