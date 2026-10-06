package com.example.demo.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskModel {
    private Long id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
}