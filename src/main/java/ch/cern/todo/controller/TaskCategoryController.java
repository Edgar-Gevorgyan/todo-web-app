package ch.cern.todo.controller;


import ch.cern.todo.dto.TaskCategory;
import ch.cern.todo.exception.CategoryNotFoundException;
import ch.cern.todo.service.TaskCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class TaskCategoryController {

    private final TaskCategoryService taskCategoryService;

    @GetMapping
    public List<TaskCategory> getCategories() {
        return taskCategoryService.getAll();
    }

    @GetMapping("{id}")
    public TaskCategory getCategory(@PathVariable Long id) {
        try {
            return taskCategoryService.getById(id);
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TaskCategory addCategory(@RequestBody TaskCategory taskCategory) {
        try {
            return taskCategoryService.add(taskCategory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public TaskCategory updateCategory(@PathVariable Long id, @RequestBody TaskCategory taskCategory) {
        if (taskCategory.name() == null || taskCategory.description() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both name and description must be provided.");
        }
        try {
            return taskCategoryService.update(id, taskCategory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public TaskCategory updatePartiallyCategory(@PathVariable Long id, @RequestBody TaskCategory taskCategory) {
        try {
            return taskCategoryService.update(id, taskCategory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id) {
        try {
            taskCategoryService.delete(id);
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
