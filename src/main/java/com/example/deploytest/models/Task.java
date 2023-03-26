package com.example.deploytest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "tasks")
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "TASK_NAME_REQUIRED")
    @Size(max = 50, message = "TASK_NAME_BAD_SIZE")
    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @NotEmpty(message = "TASK_DESCRIPTION_REQUIRED")
    @Size(max = 50, message = "TASK_DESCRIPTION_BAD_SIZE")
    @Column(unique = true, nullable = false)
    private String description;
}
