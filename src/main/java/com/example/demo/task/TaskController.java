package com.example.demo.task;

import com.example.demo.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);
        return taskRepository.save(task);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task modifyTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new NotFoundException(id);
        }

        Task existingTask = optionalTask.get();
        modelMapper.map(taskDto, existingTask);

        return taskRepository.save(existingTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
