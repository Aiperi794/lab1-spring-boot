package com.example.demo.controller;

import com.example.demo.model.TaskModel;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /** Список всех задач */
    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    /** Форма добавления новой задачи */
    @GetMapping("/task/new")
    public String newTask(Model model) {
        model.addAttribute("task", new TaskModel());
        return "tasks/form";
    }

    /** Форма редактирования */
    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        TaskModel task = taskService.findById(id);
        model.addAttribute("task", task);
        return "tasks/form";
    }

    /** Сохранение с валидацией */
    @PostMapping("/task/save")
    public String saveTask(
            @Valid @ModelAttribute("task") TaskModel task,
            BindingResult bindingResult) {

        // Если есть ошибки валидации — возвращаемся на форму
        if (bindingResult.hasErrors()) {
            return "tasks/form";
        }

        taskService.save(task);
        return "redirect:/tasks";
    }

    /** Удаление задачи */
    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }

    /** Фильтрация по статусу */
    @GetMapping("/tasks/filter")
    public String filterTasks(@RequestParam("completed") boolean isCompleted, Model model) {
        List<TaskModel> filteredTasks = taskService.findByCompleted(isCompleted);
        model.addAttribute("tasks", filteredTasks);
        return "tasks/list";
    }
}