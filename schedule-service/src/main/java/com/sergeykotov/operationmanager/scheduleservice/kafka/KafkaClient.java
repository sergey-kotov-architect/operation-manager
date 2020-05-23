package com.sergeykotov.operationmanager.scheduleservice.kafka;

import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaClient {
    public void sendSchedulingInitiatedEvent(long opGroupId) {
        //TODO: implement KafkaClient::sendSchedulingInitiatedEvent
        throw new UnsupportedOperationException();
    }

    public void sendGeneratedSchedule(long opGroupId, long start, long end, long elapsed, List<Op> schedule) {
        //TODO: implement KafkaClient::sendGeneratedSchedule
        throw new UnsupportedOperationException();
    }
}