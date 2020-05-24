package com.sergeykotov.operationmanager.operationservice.controller;

import com.sergeykotov.operationmanager.operationservice.model.OpGroup;
import com.sergeykotov.operationmanager.operationservice.service.OpGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/operation/group")
public class OpGroupController {
    private final OpGroupService opGroupService;

    @Autowired
    public OpGroupController(OpGroupService opGroupService) {
        this.opGroupService = opGroupService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid OpGroup opGroup) {
        opGroupService.create(opGroup);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid OpGroup opGroup) {
        opGroupService.update(opGroup);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        opGroupService.delete(id);
    }
}