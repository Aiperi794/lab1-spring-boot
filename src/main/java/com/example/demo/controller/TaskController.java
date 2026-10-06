package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.model.TaskModel;
import com.example.demo.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Список задач
    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    // Форма добавления новой задачи
    @GetMapping("/task/new")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/form";
    }

    // Форма редактирования задачи
    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "tasks/form";
    }

    // Сохранение задачи (создание или обновление)
    @PostMapping("/task/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        taskService.save(task);
        return "redirect:/tasks";
    }
}