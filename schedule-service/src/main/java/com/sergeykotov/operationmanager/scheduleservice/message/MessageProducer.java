package com.sergeykotov.operationmanager.scheduleservice.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergeykotov.operationmanager.scheduleservice.event.Event;
import com.sergeykotov.operationmanager.scheduleservice.event.EventType;
import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TOPIC = "SCHEDULE";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private void sendEvent(String code, String message, String body) {
        log.info("sending event to Kafka: {}", message);
        try {
            Event event = new Event(code, message, LocalDateTime.now().toString(), body);
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, json);
        } catch (Exception e) {
            log.error("failed to send event to Kafka: {}", message, e);
        }
        log.info("event has been sent to Kafka: {}", message);
    }

    public void sendSchedulingInitiatedEvent(long opGroupId) {
        String message = String.format(EventType.SCHEDULING_INITIATED.getMessage(), opGroupId);
        sendEvent(EventType.SCHEDULING_INITIATED.name(), message, "");
    }

    public void sendGeneratedSchedule(long opGroupId, long start, long end, long elapsed, List<Op> schedule) {
        String message = String.format(EventType.SCHEDULE_GENERATED.getMessage(), opGroupId);
        ScheduleEvent scheduleEvent = new ScheduleEvent(opGroupId, start, end, elapsed, schedule);
        String body;
        try {
            body = objectMapper.writeValueAsString(scheduleEvent);
        } catch (Exception e) {
            log.error("failed to create JSON to send event to Kafka: {}", message, e);
            return;
        }
        sendEvent(EventType.SCHEDULE_GENERATED.name(), message, body);
    }
}