package ch.cern.todo.service;

import ch.cern.todo.dto.Task;
import ch.cern.todo.entity.TaskCategoryEntity;
import ch.cern.todo.entity.TaskEntity;
import ch.cern.todo.exception.CategoryNotFoundException;
import ch.cern.todo.exception.TaskNotFoundException;
import ch.cern.todo.repository.TaskCategoryRepository;
import ch.cern.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskCategoryRepository taskCategoryRepository;

    private TaskService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskService(taskRepository, taskCategoryRepository);
    }

    @Test
    void canGetAllTasks() {
        // given

        // when
        underTest.getAll();

        // then
        verify(taskRepository).findAll();
    }

    @Test
    void canGetTaskById() {
        // given
        long expected = 1L;

        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setId(1L);
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        TaskEntity taskEntity = new TaskEntity(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                taskCategoryEntity
        );

        given(taskRepository.findById(anyLong())).willReturn(Optional.of(taskEntity));

        // when
        assertDoesNotThrow(() -> underTest.getById(expected));

        // then
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(taskRepository).findById(idArgumentCaptor.capture());

        assertEquals(expected, idArgumentCaptor.getValue());
    }

    @Test
    void canThrowTaskNotFoundWhenGettingTaskById() {
        // given
        long expected = 1L;

        given(taskRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        assertThrows(TaskNotFoundException.class, () -> underTest.getById(expected));
    }

    @Test
    void canAddTask() {
        // given
        Task expected = new Task(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                1L
        );


        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setId(1L);
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        TaskEntity taskEntity = new TaskEntity(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                taskCategoryEntity
        );

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.of(taskCategoryEntity));
        given(taskRepository.save(any())).willReturn(taskEntity);

        // when
        assertDoesNotThrow(() -> underTest.add(expected));

        // then
        ArgumentCaptor<TaskEntity> customerArgumentCaptor = ArgumentCaptor.forClass(TaskEntity.class);
        verify(taskRepository).save(customerArgumentCaptor.capture());

        assertEquals(expected.name(), customerArgumentCaptor.getValue().getName());
        assertEquals(expected.description(), customerArgumentCaptor.getValue().getDescription());
        assertEquals(expected.deadline(), customerArgumentCaptor.getValue().getDeadline());
    }

    @Test
    void canThrowCategoryNotFoundWhenAddingTask() {
        // given
        Task expected = new Task(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                2L
        );

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        assertThrows(CategoryNotFoundException.class, () -> underTest.add(expected));
    }

    @Test
    void canDeleteTask() {
        // given
        long expected = 1L;

        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setId(1L);
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        TaskEntity taskEntity = new TaskEntity(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                taskCategoryEntity
        );

        given(taskRepository.findById(anyLong())).willReturn(Optional.of(taskEntity));

        // when
        assertDoesNotThrow(() -> underTest.delete(expected));

        // then
        ArgumentCaptor<TaskEntity> customerArgumentCaptor = ArgumentCaptor.forClass(TaskEntity.class);
        verify(taskRepository).delete(customerArgumentCaptor.capture());

        assertEquals(taskEntity, customerArgumentCaptor.getValue());
    }

    @Test
    void canThrowTaskNotFoundWhenDeletingTaskById() {
        // given
        long expected = 1L;

        given(taskRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        assertThrows(TaskNotFoundException.class, () -> underTest.delete(expected));
    }
}