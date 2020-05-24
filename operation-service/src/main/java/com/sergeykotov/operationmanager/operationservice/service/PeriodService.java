package com.sergeykotov.operationmanager.operationservice.service;

import com.sergeykotov.operationmanager.operationservice.exception.DatabaseException;
import com.sergeykotov.operationmanager.operationservice.kafka.KafkaClient;
import com.sergeykotov.operationmanager.operationservice.kafka.PeriodEvent;
import com.sergeykotov.operationmanager.operationservice.model.Period;
import com.sergeykotov.operationmanager.operationservice.repository.PeriodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodService {
    private static final Logger log = LoggerFactory.getLogger(PeriodService.class);

    private final PeriodRepository periodRepository;
    private final KafkaClient kafkaClient;

    @Autowired
    public PeriodService(PeriodRepository periodRepository, KafkaClient kafkaClient) {
        this.periodRepository = periodRepository;
        this.kafkaClient = kafkaClient;
    }

    public void create(Period period) {
        log.info("creating period {}", period);
        try {
            periodRepository.create(period);
        } catch (Exception e) {
            log.error("failed to create period {}, {}", period, e.getMessage());
            kafkaClient.sendPeriodEvent(PeriodEvent.PERIOD_NOT_CREATED, period);
            throw new DatabaseException(e);
        }
        log.info("period {} has been created", period);
        kafkaClient.sendPeriodEvent(PeriodEvent.PERIOD_CREATED, period);
    }

    public void update(Period period) {
        log.info("updating period {}", period);
        try {
            periodRepository.update(period);
        } catch (Exception e) {
            log.error("failed to update period {}, {}", period, e.getMessage());
            kafkaClient.sendPeriodEvent(PeriodEvent.PERIOD_NOT_UPDATED, period);
            throw new DatabaseException(e);
        }
        log.info("period {} has been updated", period);
        kafkaClient.sendPeriodEvent(PeriodEvent.PERIOD_UPDATED, period);
    }

    public void delete(long id) {
        log.info("deleting period by ID {}", id);
        try {
            periodRepository.delete(id);
        } catch (Exception e) {
            log.error("failed to delete period by ID {}, {}", id, e.getMessage());
            kafkaClient.sendPeriodEvent(PeriodEvent.PERIOD_NOT_DELETED, id);
            throw new DatabaseException(e);
        }
        log.info("period has been deleted by ID {}", id);
        kafkaClient.sendPeriodEvent(PeriodEvent.PERIOD_DELETED, id);
    }
}