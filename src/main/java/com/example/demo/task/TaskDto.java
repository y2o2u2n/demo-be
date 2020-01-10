package com.example.demo.task;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
class TaskDto {
    private String name;
    private Boolean done;

    TaskDto(String name, Boolean done) {
        this.name = name;
        this.done = done;
    }
}
