package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskModel {

    private Long id;

    @NotBlank(message = "Название обязательно")
    @Size(min = 3, max = 100, message = "Название должно быть от 3 до 100 символов")
    private String name;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    private LocalDate dueDate;

    private boolean completed;
}