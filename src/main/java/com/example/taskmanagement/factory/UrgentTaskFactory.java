package com.example.taskmanagement.factory;

import com.example.taskmanagement.model.Task;

public class UrgentTaskFactory implements TaskFactory {
    @Override
    public Task createTask() {
        Task task = new Task();
        task.setPriority("Urgent");
        return task;
    }
}
