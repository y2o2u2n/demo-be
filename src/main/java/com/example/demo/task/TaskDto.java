package com.example.demo.task;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
class TaskDto {
    private String name;

    TaskDto(String name) {
        this.name = name;
    }
}
