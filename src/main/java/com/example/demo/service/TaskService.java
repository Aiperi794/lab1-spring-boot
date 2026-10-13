package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.model.TaskModel;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // ========== Конвертация ==========

    /** Entity → DTO */
    private TaskModel convertToModel(Task task) {
        if (task == null) {
            return null;
        }
        return TaskModel.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .completed(task.getCompleted())
                .build();
    }

    /** DTO → Entity */
    private Task convertToEntity(TaskModel model) {
        if (model == null) {
            return null;
        }
        return Task.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .dueDate(model.getDueDate())
                .completed(model.isCompleted())
                .build();
    }

    // ========== CRUD ==========

    public List<TaskModel> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    public TaskModel findById(Long id) {
        return taskRepository.findById(id)
                .map(this::convertToModel)
                .orElse(null);
    }

    public TaskModel save(TaskModel model) {
        Task entity = convertToEntity(model);
        Task saved = taskRepository.save(entity);
        return convertToModel(saved);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    // ========== Фильтрация ==========

    /** Фильтрация по статусу */
    public List<TaskModel> findByCompleted(boolean isCompleted) {
        return taskRepository.findAllByCompleted(isCompleted)
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    /** Фильтрация по диапазону дат */
    public List<TaskModel> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findAllByDueDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    /** Фильтрация по статусу И диапазону дат */
    public List<TaskModel> findByCompletedAndDateRange(boolean isCompleted, LocalDate startDate, LocalDate endDate) {
        return taskRepository.findAllByCompletedAndDueDateBetween(isCompleted, startDate, endDate)
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }
}