package com.sergeykotov.operationmanager.scheduleservice.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergeykotov.operationmanager.scheduleservice.event.Event;
import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import com.sergeykotov.operationmanager.scheduleservice.service.OpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final OpService opService;

    @Autowired
    public MessageConsumer(OpService opService) {
        this.opService = opService;
    }

    private Optional<String> getBody(String message) {
        Event event;
        try {
            event = objectMapper.readValue(message, Event.class);
        } catch (Exception e) {
            log.info("failed to parse message: {}", message);
            return Optional.empty();
        }
        if (event == null) {
            log.warn("empty event arrived");
            return Optional.empty();
        }
        log.info("new event arrived: {}", event.toString());
        return Optional.ofNullable(event.getBody());
    }

    @KafkaListener(topics = "OP_CREATED", groupId = "OPERATION")
    public void listenOpCreated(String message) {
        Optional<String> body = getBody(message);
        if (!body.isPresent()) {
            return;
        }
        Op op;
        try {
            op = objectMapper.readValue(body.get(), Op.class);
        } catch (Exception e) {
            log.info("failed to parse body: {}", body);
            return;
        }
        opService.create(op);
    }

    @KafkaListener(topics = "OP_UPDATED", groupId = "OPERATION")
    public void listenOpUpdated(String message) {
        Optional<String> body = getBody(message);
        if (!body.isPresent()) {
            return;
        }
        Op op;
        try {
            op = objectMapper.readValue(body.get(), Op.class);
        } catch (Exception e) {
            log.info("failed to parse body: {}", body);
            return;
        }
        opService.update(op);
    }

    @KafkaListener(topics = "OP_DELETED", groupId = "OPERATION")
    public void listenOpDeleted(String message) {
        Optional<String> body = getBody(message);
        if (!body.isPresent()) {
            return;
        }
        long id;
        try {
            id = Long.parseLong(body.get());
        } catch (Exception e) {
            log.info("failed to parse body: {}", body);
            return;
        }
        opService.delete(id);
    }

    @KafkaListener(topics = "OPS_UPDATED", groupId = "OPERATION")
    public void listenOpsUpdated(String message) {
        Optional<String> body = getBody(message);
        if (!body.isPresent()) {
            return;
        }
        List<Op> ops;
        try {
            ops = objectMapper.readValue(body.get(), new TypeReference<List<Op>>() {});
        } catch (Exception e) {
            log.info("failed to parse body: {}", body);
            return;
        }
        opService.update(ops);
    }
}