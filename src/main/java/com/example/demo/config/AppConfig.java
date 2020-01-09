package com.example.demo.config;

import com.example.demo.task.Task;
import com.example.demo.task.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner(TaskRepository repository) {
        return args -> {
            repository.save(new Task("Go market"));
            repository.save(new Task("Do homework"));
            repository.save(new Task("Clean room"));

            repository.findAll()
                    .forEach(s -> log.info(s.toString()));

            log.info(repository.findById(1L).toString());
            log.info(repository.findByName("Do homework").toString());
        };
    }
}
