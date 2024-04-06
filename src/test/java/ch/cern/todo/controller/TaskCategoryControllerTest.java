package ch.cern.todo.controller;

import ch.cern.todo.dto.TaskCategory;
import ch.cern.todo.exception.CategoryNotFoundException;
import ch.cern.todo.service.TaskCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskCategoryController.class)
class TaskCategoryControllerTest {
    final private String BASE_URL = "/categories";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskCategoryService service;


    @Test
    void canGetAllTaskCategories() throws Exception {
        when(service.getAll()).thenReturn(List.of());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canGetTaskCategoryById() throws Exception {
        long expected = 1L;
        TaskCategory taskCategory = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );

        when(service.getById(anyLong())).thenReturn(taskCategory);

        mockMvc.perform(get(BASE_URL + "/" + expected))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canGetTaskCategoryByIdWhenTaskNotFound() throws Exception {
        long expected = 1L;
        when(service.getById(anyLong())).thenThrow(new CategoryNotFoundException("Category with id: " + expected + " does not exists."));

        mockMvc.perform(get(BASE_URL + "/" + expected))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void canPostTaskCategory() throws Exception {
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );

        when(service.add(any())).thenReturn(expected);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NAME\",\"description\":\"DESCRIPTION\"}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void canPutTaskCategory() throws Exception {
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );

        when(service.update(anyLong(), any())).thenReturn(expected);

        mockMvc.perform(put(BASE_URL + "/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NAME\",\"description\":\"DESCRIPTION\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canRespond400WhenPuttingWithPartialDataTaskCategory() throws Exception {
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );

        when(service.update(anyLong(), any())).thenReturn(expected);

        mockMvc.perform(put(BASE_URL + "/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"DESCRIPTION\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void canPatchTaskCategory() throws Exception {
        TaskCategory expected = new TaskCategory(
                1L,
                "NAME",
                "DESCRIPTION",
                List.of()
        );

        when(service.update(anyLong(), any())).thenReturn(expected);

        mockMvc.perform(patch(BASE_URL + "/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"DESCRIPTION\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canDeleteCategoryTask() throws Exception {
        long expected = 1L;

        mockMvc.perform(delete(BASE_URL + "/" + expected))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}