package com.example.demo.task;

import com.example.demo.common.BaseControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
        TaskDto taskDto = new TaskDto("Eat something");

        mockMvc.perform(
                post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(taskDto))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.done").exists());
    }

    @Test
    public void removeTask() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}