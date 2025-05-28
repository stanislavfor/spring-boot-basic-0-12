package com.example.taskmanagement.factory;

import com.example.taskmanagement.model.Task;

public class RegularTaskFactory implements TaskFactory {
    @Override
    public Task createTask() {
        Task task = new Task();
        task.setPriority("Regular");
        return task;
    }
}
