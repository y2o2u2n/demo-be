package com.example.demo.task;

import com.example.demo.common.BaseControllerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends BaseControllerTest {
    @Autowired
    TaskRepository taskRepository;

    @Test
    public void getTasks() throws Exception {
        mockMvc.perform(get("/api/v1/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].done").exists());
    }

    @Test
    public void addTask() throws Exception {
        TaskDto taskDto = new TaskDto("Eat something", Boolean.FALSE);

        mockMvc.perform(
                post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(taskDto))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(taskDto.getName()))
                .andExpect(jsonPath("$.done").value(taskDto.getDone()));
    }

    @Test
    public void modifyTask() throws Exception {
        Task task = taskRepository.save(new Task("Task", Boolean.TRUE));
        Long id = task.getId();

        TaskDto taskDto = new TaskDto("Updated Task", Boolean.FALSE);

        mockMvc.perform(
                patch("/api/v1/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(taskDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(taskDto.getName()))
                .andExpect(jsonPath("$.done").value(taskDto.getDone()));
    }

    @Test
    public void modifyTask_404() throws Exception{
        Long unknownId = 1024L;

        mockMvc.perform(
                patch("/api/v1/tasks/{id}", unknownId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new TaskDto("Updated Task", Boolean.FALSE)))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeTask() throws Exception {
        Task task = taskRepository.save(new Task("Task", Boolean.TRUE));
        Long id = task.getId();

        mockMvc.perform(delete("/api/v1/tasks/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());

        Optional<Task> optionalTask = taskRepository.findById(id);
        Assert.assertTrue(optionalTask.isEmpty());
    }
}