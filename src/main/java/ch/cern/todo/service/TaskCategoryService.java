package ch.cern.todo.service;

import ch.cern.todo.dto.TaskCategory;
import ch.cern.todo.entity.TaskCategoryEntity;
import ch.cern.todo.exception.CategoryNameAlreadyExistsException;
import ch.cern.todo.exception.CategoryNotFoundException;
import ch.cern.todo.repository.TaskCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;

    static TaskCategory convertToTaskCategory(TaskCategoryEntity taskCategoryEntity) {
        return new TaskCategory(
                taskCategoryEntity.getId(),
                taskCategoryEntity.getName(),
                taskCategoryEntity.getDescription(),
                taskCategoryEntity.getTasks().stream().map(TaskService::convertToTask).collect(Collectors.toList())
        );
    }

    @Transactional
    public List<TaskCategory> getAll() {
        return taskCategoryRepository.findAll().stream().map(TaskCategoryService::convertToTaskCategory).collect(Collectors.toList());
    }

    public TaskCategory getById(long id) throws CategoryNotFoundException {
        TaskCategoryEntity taskCategoryEntity = getTaskCategoryEntityById(id);
        return convertToTaskCategory(taskCategoryEntity);
    }

    @Transactional
    public TaskCategory add(TaskCategory taskCategory) throws CategoryNameAlreadyExistsException {
        if (taskCategoryRepository.existsByName(taskCategory.name())) {
            throw new CategoryNameAlreadyExistsException("Category name : " + taskCategory.name() + " already exists.");
        }

        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setName(taskCategory.name());
        taskCategoryEntity.setDescription(taskCategory.description());

        TaskCategoryEntity createdTaskCategoryEntity = taskCategoryRepository.save(taskCategoryEntity);
        return convertToTaskCategory(createdTaskCategoryEntity);
    }

    @Transactional
    public TaskCategory update(long id, TaskCategory taskCategory) throws CategoryNotFoundException, CategoryNameAlreadyExistsException {
        TaskCategoryEntity taskCategoryEntity = getTaskCategoryEntityById(id);
        if (taskCategory.name() != null) {
            if (!taskCategoryEntity.getName().equals(taskCategory.name()) &&
                    taskCategoryRepository.existsByName(taskCategory.name())) {
                throw new CategoryNameAlreadyExistsException("Category name : " + taskCategory.name() + " already exists.");
            }
            taskCategoryEntity.setName(taskCategory.name());
        }
        if (taskCategory.description() != null) {
            taskCategoryEntity.setDescription(taskCategory.description());
        }

        TaskCategoryEntity updatedTaskCategoryEntity = taskCategoryRepository.save(taskCategoryEntity);
        return convertToTaskCategory(updatedTaskCategoryEntity);
    }

    public void delete(long id) throws CategoryNotFoundException {
        TaskCategoryEntity taskCategoryEntity = getTaskCategoryEntityById(id);
        taskCategoryRepository.delete(taskCategoryEntity);
    }

    private TaskCategoryEntity getTaskCategoryEntityById(long id) throws CategoryNotFoundException {
        Optional<TaskCategoryEntity> taskCategoryEntity = taskCategoryRepository.findById(id);
        if (taskCategoryEntity.isEmpty()) {
            throw new CategoryNotFoundException("Category with id: " + id + " does not exists.");
        }
        return taskCategoryEntity.get();
    }
}
