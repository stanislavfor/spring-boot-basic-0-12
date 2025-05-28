package com.example.taskmanagement.repository;

import com.example.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPriority(String priority);
    List<Task> findByCompleted(boolean completed);
}
