package com.sergeykotov.operationmanager.operationservice.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergeykotov.operationmanager.operationservice.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class MessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
    }
}