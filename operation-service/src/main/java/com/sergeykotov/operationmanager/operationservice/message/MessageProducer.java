package com.sergeykotov.operationmanager.operationservice.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergeykotov.operationmanager.operationservice.event.*;
import com.sergeykotov.operationmanager.operationservice.model.*;
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
    private static final String TOPIC = "OPERATION";

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

    public void sendTaskEvent(TaskEvent taskEvent, Task task) {
        String message = String.format(taskEvent.getMessage(), task.toString());
        String body = null;
        try {
            body = objectMapper.writeValueAsString(task);
        } catch (Exception e) {
            log.error("failed to create JSON to send event to Kafka: {}", message, e);
        }
        sendEvent(taskEvent.name(), message, body);
    }

    public void sendTaskEvent(TaskEvent taskEvent, long id) {
        String message = String.format(taskEvent.getMessage(), id);
        sendEvent(taskEvent.name(), message, String.valueOf(id));
    }

    public void sendExecutorEvent(ExecutorEvent executorEvent, Executor executor) {
        String message = String.format(executorEvent.getMessage(), executor.toString());
        String body = null;
        try {
            body = objectMapper.writeValueAsString(executor);
        } catch (Exception e) {
            log.error("failed to create JSON to send event to Kafka: {}", message, e);
        }
        sendEvent(executorEvent.name(), message, body);
    }

    public void sendExecutorEvent(ExecutorEvent executorEvent, long id) {
        String message = String.format(executorEvent.getMessage(), id);
        sendEvent(executorEvent.name(), message, String.valueOf(id));
    }

    public void sendPeriodEvent(PeriodEvent periodEvent, Period period) {
        String message = String.format(periodEvent.getMessage(), period.toString());
        String body = null;
        try {
            body = objectMapper.writeValueAsString(period);
        } catch (Exception e) {
            log.error("failed to create JSON to send event to Kafka: {}", message, e);
        }
        sendEvent(periodEvent.name(), message, body);
    }

    public void sendPeriodEvent(PeriodEvent periodEvent, long id) {
        String message = String.format(periodEvent.getMessage(), id);
        sendEvent(periodEvent.name(), message, String.valueOf(id));
    }

    public void sendOpGroupEvent(OpGroupEvent opGroupEvent, OpGroup opGroup) {
        String message = String.format(opGroupEvent.getMessage(), opGroup.toString());
        String body = null;
        try {
            body = objectMapper.writeValueAsString(opGroup);
        } catch (Exception e) {
            log.error("failed to create JSON to send event to Kafka: {}", message, e);
        }
        sendEvent(opGroupEvent.name(), message, body);
    }

    public void sendOpGroupEvent(OpGroupEvent opGroupEvent, long id) {
        String message = String.format(opGroupEvent.getMessage(), id);
        sendEvent(opGroupEvent.name(), message, String.valueOf(id));
    }

    public void sendOpEvent(OpEvent opEvent, Op op) {
        String message = String.format(opEvent.getMessage(), op.toString());
        String body = null;
        try {
            body = objectMapper.writeValueAsString(op);
        } catch (Exception e) {
            log.error("failed to create JSON to send event to Kafka: {}", message, e);
        }
        sendEvent(opEvent.name(), message, body);
    }

    public void sendOpEvent(OpEvent opEvent, long id) {
        String message = String.format(opEvent.getMessage(), id);
        sendEvent(opEvent.name(), message, String.valueOf(id));
    }

    public void sendOpEvent(OpEvent opEvent, long opGroupId, List<Op> ops) {
        String message = String.format(opEvent.getMessage(), opGroupId);
        String body = null;
        try {
            body = objectMapper.writeValueAsString(ops);
        } catch (Exception e) {
            log.error("failed to create JSON to send event to Kafka: {}", message, e);
        }
        sendEvent(opEvent.name(), message, body);
    }
}