package com.example.demo.config;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TaskDataInitializer implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) {
        System.out.println("========== ТЕСТ JPA: добавление и извлечение задач ==========");

        // 1. Добавляем тестовые задачи
        Task task1 = Task.builder()
                .name("Тестовая задача 1 из JPA")
                .description("Создана через Task.builder()")
                .dueDate(LocalDate.now().plusDays(3))
                .completed(false)
                .build();

        Task task2 = Task.builder()
                .name("Тестовая задача 2 из JPA")
                .description("Проверка репозитория")
                .dueDate(LocalDate.now().plusDays(7))
                .completed(true)
                .build();

        taskRepository.save(task1);
        taskRepository.save(task2);

        System.out.println("✅ Добавлено 2 задачи через TaskRepository.save()");

        // 2. Извлекаем все задачи
        List<Task> allTasks = taskRepository.findAll();
        System.out.println("📋 Всего задач в БД: " + allTasks.size());
        for (Task t : allTasks) {
            System.out.println("   → ID=" + t.getId()
                    + ", name=" + t.getName()
                    + ", completed=" + t.getCompleted());
        }

        // 3. Извлекаем одну задачу по ID
        taskRepository.findById(task1.getId()).ifPresent(t ->
                System.out.println("🔍 Найдена задача по ID: " + t.getName())
        );

        System.out.println("========== ТЕСТ JPA ЗАВЕРШЁН ==========");
    }
}