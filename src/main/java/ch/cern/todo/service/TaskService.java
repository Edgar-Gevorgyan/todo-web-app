package ch.cern.todo.service;

import ch.cern.todo.dto.Task;
import ch.cern.todo.entity.TaskCategoryEntity;
import ch.cern.todo.entity.TaskEntity;
import ch.cern.todo.exception.CategoryNotFoundException;
import ch.cern.todo.exception.TaskNotFoundException;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    static Task convertToTask(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getName(),
                taskEntity.getDescription(),
                taskEntity.getDeadline(),
                taskEntity.getCategory().getId()
        );
    }

    @Transactional
    public Stream<Task> getAll() {
        return taskRepository.findAll().stream().map(TaskService::convertToTask);
    }

    public Task getById(long id) throws TaskNotFoundException {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        if (taskEntity.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + id + " does not exists.");
        }
        return convertToTask(taskEntity.get());
    }

    @Transactional
    public Task add(Task task) throws CategoryNotFoundException {
        Optional<TaskCategoryEntity> taskCategoryEntity = taskCategoryRepository.findById(task.categoryId());
        if (taskCategoryEntity.isEmpty()) {
            throw new CategoryNotFoundException("Category with id: " + task.categoryId() + " does not exists.");
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.name());
        taskEntity.setDescription(task.description());
        taskEntity.setDeadline(task.deadline());
        taskEntity.setCategory(taskCategoryEntity.get());

        TaskEntity createdTaskEntity = taskRepository.save(taskEntity);
        return convertToTask(createdTaskEntity);
    }

    public void delete(long id) throws TaskNotFoundException {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        if (taskEntity.isEmpty()) {
            throw new TaskNotFoundException("Task with id: " + id + " does not exists.");
        }
        taskRepository.delete(taskEntity.get());
    }
}
