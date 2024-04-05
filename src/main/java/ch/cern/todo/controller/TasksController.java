package ch.cern.todo.controller;


import ch.cern.todo.dto.Task;
import ch.cern.todo.exception.TaskNotFoundException;
import ch.cern.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;


@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TasksController {

    private final TaskService taskService;

    @GetMapping
    public Stream<Task> getTasks() {
        return taskService.getAll();
    }

    @GetMapping("{id}")
    public Task getTask(@PathVariable Long id) {
        try {
            return taskService.getById(id);
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Task addTask(@RequestBody Task task) {
        try {
            return taskService.add(task);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable int id) {
        try {
            taskService.delete(id);
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
