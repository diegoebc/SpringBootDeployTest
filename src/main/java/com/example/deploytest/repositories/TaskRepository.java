package com.example.deploytest.repositories;

import com.example.deploytest.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task,Long> {
    boolean existsByName(String name);
}
