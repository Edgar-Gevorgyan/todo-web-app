package ch.cern.todo.service;

import ch.cern.todo.dto.Task;
import ch.cern.todo.entity.TaskEntity;
import ch.cern.todo.exception.TaskNotFoundException;
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

    private static Task convertToTask(TaskEntity taskEntity) {
        return new Task(taskEntity.getId(), taskEntity.getName(), taskEntity.getDescription(), taskEntity.getDeadline());
    }

    private static TaskEntity convertToTaskEntity(Task task) {
        return new TaskEntity(task.id(), task.name(), task.description(), task.deadline());
    }

    @Transactional
    public Stream<Task> getAll() {
        return taskRepository.findAll().stream().map(TaskService::convertToTask);
    }

    public Task getById(long id) throws TaskNotFoundException {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        if (taskEntity.isPresent()) {
            return convertToTask(taskEntity.get());
        }
        throw new TaskNotFoundException("Task with id: " + id + " does not exists.");
    }

    public Task add(Task task) {
        TaskEntity taskEntity = convertToTaskEntity(task);
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
