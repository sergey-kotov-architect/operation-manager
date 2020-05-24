package com.sergeykotov.operationmanager.operationservice.service;

import com.sergeykotov.operationmanager.operationservice.exception.DatabaseException;
import com.sergeykotov.operationmanager.operationservice.kafka.KafkaClient;
import com.sergeykotov.operationmanager.operationservice.kafka.TaskEvent;
import com.sergeykotov.operationmanager.operationservice.model.Task;
import com.sergeykotov.operationmanager.operationservice.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final KafkaClient kafkaClient;

    @Autowired
    public TaskService(TaskRepository taskRepository, KafkaClient kafkaClient) {
        this.taskRepository = taskRepository;
        this.kafkaClient = kafkaClient;
    }

    public void create(Task task) {
        log.info("creating task {}", task);
        try {
            taskRepository.create(task);
        } catch (Exception e) {
            log.error("failed to create task {}, {}", task, e.getMessage());
            kafkaClient.sendTaskEvent(TaskEvent.TASK_NOT_CREATED, task);
            throw new DatabaseException(e);
        }
        log.info("task {} has been created", task);
        kafkaClient.sendTaskEvent(TaskEvent.TASK_CREATED, task);
    }

    public void update(Task task) {
        log.info("updating task {}", task);
        try {
            taskRepository.update(task);
        } catch (Exception e) {
            log.error("failed to update task {}, {}", task, e.getMessage());
            kafkaClient.sendTaskEvent(TaskEvent.TASK_NOT_UPDATED, task);
            throw new DatabaseException(e);
        }
        log.info("task {} has been updated", task);
        kafkaClient.sendTaskEvent(TaskEvent.TASK_UPDATED, task);
    }

    public void delete(long id) {
        log.info("deleting task by ID {}", id);
        try {
            taskRepository.delete(id);
        } catch (Exception e) {
            log.error("failed to delete task by ID {}, {}", id, e.getMessage());
            kafkaClient.sendTaskEvent(TaskEvent.TASK_NOT_DELETED, id);
            throw new DatabaseException(e);
        }
        log.info("task has been deleted by ID {}", id);
        kafkaClient.sendTaskEvent(TaskEvent.TASK_DELETED, id);
    }
}