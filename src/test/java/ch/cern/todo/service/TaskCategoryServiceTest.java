package ch.cern.todo.service;

import ch.cern.todo.dto.TaskCategory;
import ch.cern.todo.entity.TaskCategoryEntity;
import ch.cern.todo.exception.CategoryNameAlreadyExistsException;
import ch.cern.todo.exception.CategoryNotFoundException;
import ch.cern.todo.repository.TaskCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskCategoryServiceTest {
    @Mock
    private TaskCategoryRepository taskCategoryRepository;

    private TaskCategoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskCategoryService(taskCategoryRepository);
    }

    @Test
    void canGetAllTaskCategories() {
        // given

        // when
        underTest.getAll();

        // then
        verify(taskCategoryRepository).findAll();
    }

    @Test
    void canGetTaskCategoryById() {
        // given
        long expected = 1L;

        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setId(1L);
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.of(taskCategoryEntity));

        // when
        assertDoesNotThrow(() -> underTest.getById(expected));

        // then
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(taskCategoryRepository).findById(idArgumentCaptor.capture());

        assertEquals(expected, idArgumentCaptor.getValue());
    }

    @Test
    void canThrowCategoryNotFoundWhenGettingTaskCategoryById() {
        // given
        long expected = 1L;

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        assertThrows(CategoryNotFoundException.class, () -> underTest.getById(expected));
    }

    @Test
    void canAddTaskCategory() {
        // given
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );


        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        given(taskCategoryRepository.existsByName(any())).willReturn(false);
        given(taskCategoryRepository.save(any())).willReturn(taskCategoryEntity);

        // when
        assertDoesNotThrow(() -> underTest.add(expected));

        // then
        ArgumentCaptor<TaskCategoryEntity> customerArgumentCaptor = ArgumentCaptor.forClass(TaskCategoryEntity.class);
        verify(taskCategoryRepository).save(customerArgumentCaptor.capture());

        assertEquals(expected.name(), customerArgumentCaptor.getValue().getName());
        assertEquals(expected.description(), customerArgumentCaptor.getValue().getDescription());
    }

    @Test
    void canThrowCategoryNameAlreadyExistsWhenAddingTaskCategory() {
        // given
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );

        given(taskCategoryRepository.existsByName(any())).willReturn(true);

        // when
        assertThrows(CategoryNameAlreadyExistsException.class, () -> underTest.add(expected));
    }

    @Test
    void canUpdateTaskCategory() {
        // given
        TaskCategory expected = new TaskCategory(
                1L,
                "UPDATED",
                "DESCRIPTION",
                List.of()
        );


        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setId(1L);
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.of(taskCategoryEntity));
        given(taskCategoryRepository.save(any())).willReturn(taskCategoryEntity);

        // when
        assertDoesNotThrow(() -> underTest.update(1L, expected));

        // then
        ArgumentCaptor<TaskCategoryEntity> customerArgumentCaptor = ArgumentCaptor.forClass(TaskCategoryEntity.class);
        verify(taskCategoryRepository).save(customerArgumentCaptor.capture());

        assertEquals(expected.name(), customerArgumentCaptor.getValue().getName());
        assertEquals(expected.description(), customerArgumentCaptor.getValue().getDescription());
    }

    @Test
    void canThrowCategoryNotFoundWhenUpdatingTaskCategory() {
        // given
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        assertThrows(CategoryNotFoundException.class, () -> underTest.update(1L, expected));
    }

    @Test
    void canThrowCategoryNameAlreadyExistsWhenUpdatingTaskCategory() {
        // given
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME_UPDATED",
                "DESCRIPTION",
                List.of()
        );

        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setId(1L);
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.of(taskCategoryEntity));
        given(taskCategoryRepository.existsByName(any())).willReturn(true);

        // when
        assertThrows(CategoryNameAlreadyExistsException.class, () -> underTest.update(1L, expected));
    }

    @Test
    void canDeleteTaskCategory() {
        // given
        long expected = 1L;

        TaskCategoryEntity taskCategoryEntity = new TaskCategoryEntity();
        taskCategoryEntity.setId(1L);
        taskCategoryEntity.setName("NAME");
        taskCategoryEntity.setDescription("DESCRIPTION");

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.of(taskCategoryEntity));

        // when
        assertDoesNotThrow(() -> underTest.delete(expected));

        // then
        ArgumentCaptor<TaskCategoryEntity> customerArgumentCaptor = ArgumentCaptor.forClass(TaskCategoryEntity.class);
        verify(taskCategoryRepository).delete(customerArgumentCaptor.capture());

        assertEquals(taskCategoryEntity, customerArgumentCaptor.getValue());
    }

    @Test
    void canThrowCategoryNotFoundWhenDeletingTaskCategoryById() {
        // given
        long expected = 1L;

        given(taskCategoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        assertThrows(CategoryNotFoundException.class, () -> underTest.delete(expected));
    }
}