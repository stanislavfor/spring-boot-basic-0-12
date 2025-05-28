package com.example.taskmanagement.observer;

import com.example.taskmanagement.model.Task;


public interface TaskObserver {
    void update(Task task);
}
