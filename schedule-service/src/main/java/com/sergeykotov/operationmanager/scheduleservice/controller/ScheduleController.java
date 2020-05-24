package com.sergeykotov.operationmanager.scheduleservice.controller;

import com.sergeykotov.operationmanager.scheduleservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createSchedulingTask(@RequestParam(name = "op_group_id") long opGroupId) {
        scheduleService.createSchedulingTask(opGroupId);
    }
}