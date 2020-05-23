package com.sergeykotov.operationmanager.scheduleservice.service;

import com.sergeykotov.operationmanager.scheduleservice.model.Executor;
import com.sergeykotov.operationmanager.scheduleservice.model.Op;
import com.sergeykotov.operationmanager.scheduleservice.model.Period;
import com.sergeykotov.operationmanager.scheduleservice.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OptimisationService {
    public List<Op> generateOptimalSchedule(List<Op> ops) {
        List<List<Op>> schedules = getAllPossibleSchedules(ops);
        return selectOptimalSchedule(schedules);
    }

    private List<List<Op>> getAllPossibleSchedules(List<Op> ops) {
        List<List<Op>> schedules = new ArrayList<>();
        Set<Task> tasks = ops.stream().map(Op::getTask).collect(Collectors.toSet());
        Set<Period> periods = ops.stream().map(Op::getPeriod).collect(Collectors.toSet());
        for (Task task : tasks) {
            for (Period period : periods) {
                List<Op> opsForExecutor = ops.stream()
                        .filter(o -> o.getTask().equals(task) && o.getPeriod().equals(period))
                        .collect(Collectors.toList());
                List<List<Op>> newSchedules = new ArrayList<>();
                for (Op op : opsForExecutor) {
                    if (schedules.isEmpty()) {
                        List<Op> newSchedule = new ArrayList<>();
                        newSchedule.add(op);
                        newSchedules.add(newSchedule);
                    } else {
                        for (List<Op> s : schedules) {
                            List<Op> newSchedule = new ArrayList<>(s);
                            newSchedule.add(op);
                            newSchedules.add(newSchedule);
                        }
                    }
                }
                schedules = newSchedules;
            }
        }
        return schedules;
    }

    private List<Op> selectOptimalSchedule(List<List<Op>> schedules) {
        return schedules.stream().min(Comparator.comparingDouble(this::deviation)).orElse(Collections.emptyList());
    }

    private double deviation(List<Op> ops) {
        Set<Executor> executors = ops.stream().map(Op::getExecutor).collect(Collectors.toSet());
        double mean = ops.stream().mapToDouble(Op::getCost).sum() / executors.size();
        double deviation = 0.0;
        for (Executor executor : executors) {
            double cost = ops.stream().filter(o -> o.getExecutor().equals(executor)).mapToDouble(Op::getCost).sum();
            deviation += Math.abs(cost - mean);
        }
        return deviation;
    }
}