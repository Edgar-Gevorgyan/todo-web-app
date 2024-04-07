package ch.cern.todo.controller;

import ch.cern.todo.dto.Task;
import ch.cern.todo.exception.TaskNotFoundException;
import ch.cern.todo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TasksControllerTest {
    final private String BASE_URL = "/api/tasks";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;


    @Test
    void canGetAllTasks() throws Exception {
        when(service.getAll()).thenReturn(Stream.of());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canGetTaskById() throws Exception {
        long expected = 1L;
        Task task = new Task(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                1L
        );

        when(service.getById(anyLong())).thenReturn(task);

        mockMvc.perform(get(BASE_URL + "/" + expected))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canGetTaskByIdWhenTaskNotFound() throws Exception {
        long expected = 1L;
        when(service.getById(anyLong())).thenThrow(new TaskNotFoundException("Task with id: " + expected + " does not exists."));

        mockMvc.perform(get(BASE_URL + "/" + expected))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void canPostTask() throws Exception {
        Task expected = new Task(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                1L
        );

        when(service.add(any())).thenReturn(expected);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NAME\",\"description\":\"DESCRIPTION\",\"deadline\":\"2024-04-06T00:00:00\",\"categoryId\":\"1\"}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void canPutTask() throws Exception {
        Task expected = new Task(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                1L
        );

        when(service.update(anyLong(), any())).thenReturn(expected);

        mockMvc.perform(put(BASE_URL + "/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NAME\",\"description\":\"DESCRIPTION\",\"deadline\":\"2024-04-06T00:00:00\",\"categoryId\":\"1\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canRespond400WhenPuttingWithPartialDataTask() throws Exception {
        Task expected = new Task(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                1L
        );

        mockMvc.perform(put(BASE_URL + "/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NAME\",\"description\":\"DESCRIPTION\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void canPatchTask() throws Exception {
        Task expected = new Task(
                1L,
                "NAME",
                "DESCRIPTION",
                LocalDateTime.now(),
                1L
        );

        when(service.update(anyLong(), any())).thenReturn(expected);

        mockMvc.perform(patch(BASE_URL + "/" + expected.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"DESCRIPTION\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void canDeleteTask() throws Exception {
        long expected = 1L;

        mockMvc.perform(delete(BASE_URL + "/" + expected))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}