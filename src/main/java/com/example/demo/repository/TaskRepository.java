package com.example.demo.repository;

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /** Фильтрация по статусу */
    List<Task> findAllByCompleted(boolean completed);

    /** Фильтрация по диапазону дат (between) */
    List<Task> findAllByDueDateBetween(LocalDate startDate, LocalDate endDate);

    /** Фильтрация по статусу И диапазону дат */
    List<Task> findAllByCompletedAndDueDateBetween(boolean completed, LocalDate startDate, LocalDate endDate);
}