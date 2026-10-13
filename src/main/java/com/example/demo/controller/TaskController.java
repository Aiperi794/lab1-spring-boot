package com.example.demo.controller;

import com.example.demo.model.TaskModel;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    // ========== Фильтрация ==========

    /** Фильтрация по статусу */
    @GetMapping("/tasks/filter")
    public String filterByStatus(@RequestParam("completed") boolean isCompleted, Model model) {
        List<TaskModel> filteredTasks = taskService.findByCompleted(isCompleted);
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("filterInfo",
                isCompleted ? "Показаны только выполненные задачи" : "Показаны только невыполненные задачи");
        return "tasks/list";
    }

    /** Фильтрация по диапазону дат */
    @GetMapping("/tasks/filter-by-date")
    public String filterByDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        List<TaskModel> filteredTasks = taskService.findByDateRange(startDate, endDate);
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("filterInfo",
                "Показаны задачи с " + startDate + " по " + endDate);
        return "tasks/list";
    }

    /** Фильтрация по статусу И диапазону дат */
    @GetMapping("/tasks/filter-full")
    public String filterFull(
            @RequestParam(value = "completed", required = false) Boolean isCompleted,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        List<TaskModel> filteredTasks;

        if (isCompleted != null && startDate != null && endDate != null) {
            filteredTasks = taskService.findByCompletedAndDateRange(isCompleted, startDate, endDate);
            model.addAttribute("filterInfo",
                    "Показаны задачи: " + (isCompleted ? "выполненные" : "невыполненные")
                            + " с " + startDate + " по " + endDate);
        } else if (isCompleted != null) {
            filteredTasks = taskService.findByCompleted(isCompleted);
            model.addAttribute("filterInfo",
                    isCompleted ? "Показаны только выполненные" : "Показаны только невыполненные");
        } else if (startDate != null && endDate != null) {
            filteredTasks = taskService.findByDateRange(startDate, endDate);
            model.addAttribute("filterInfo",
                    "Показаны задачи с " + startDate + " по " + endDate);
        } else {
            filteredTasks = taskService.findAll();
            model.addAttribute("filterInfo", "Показаны все задачи");
        }

        model.addAttribute("tasks", filteredTasks);
        return "tasks/list";
    }
}