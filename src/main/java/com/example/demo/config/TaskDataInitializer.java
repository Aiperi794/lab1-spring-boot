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
        System.out.println("========== ТЕСТ JPA: проверка работы репозитория ==========");

        // ⚠️ Блок добавления закомментирован, чтобы не дублировать задачи при каждом запуске.
        // Раскомментируйте ТОЛЬКО один раз, если нужно добавить тестовые задачи в пустую БД.
        /*
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
        */

        // Чтение данных — безопасно, не изменяет БД
        List<Task> allTasks = taskRepository.findAll();
        System.out.println("📋 Всего задач в БД: " + allTasks.size());
        for (Task t : allTasks) {
            System.out.println("   → ID=" + t.getId()
                    + ", name=" + t.getName()
                    + ", completed=" + t.getCompleted());
        }

        // Поиск одной задачи
        if (!allTasks.isEmpty()) {
            Long firstId = allTasks.get(0).getId();
            taskRepository.findById(firstId).ifPresent(t ->
                    System.out.println("🔍 Найдена задача по ID=" + firstId + ": " + t.getName())
            );
        }

        System.out.println("========== ТЕСТ JPA ЗАВЕРШЁН ==========");
    }
}