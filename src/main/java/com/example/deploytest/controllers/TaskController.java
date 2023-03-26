package com.example.deploytest.controllers;

import com.example.deploytest.exceptions.TaskAlreadyExistsException;
import com.example.deploytest.exceptions.TaskNotFoundException;
import com.example.deploytest.models.Task;
import com.example.deploytest.repositories.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task){
        boolean taskExists = taskRepository.existsByName(task.getName());
        if(taskExists){
            throw new TaskAlreadyExistsException("TASK_ALREADY_EXISTS");
        }
        Task newTask = taskRepository.save(task);
        return ResponseEntity
                .created(URI.create("/tasks/"+newTask.getId()))
                .body(newTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new TaskNotFoundException("TASK_NOT_FOUND");
        }
        return ResponseEntity
                .ok()
                .body(task.get());
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(){
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> taskList =  StreamSupport
                .stream(taskIterable.spliterator(),false)
                .toList();
        return ResponseEntity
                .ok()
                .body(taskList);
    }
}
