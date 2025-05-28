package com.example.taskmanagement.observer;

import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements TaskObserver {

    @Autowired
    private EmailService emailService;

    @Override
    public void update(Task task) {
        String subject = "Task Update: " + task.getTitle();
        String text = "Task " + task.getTitle() + " has been updated. New status: " + (task.isCompleted() ? "Completed" : "Pending");
        task.getSubscribers().forEach(subscriber -> {
            emailService.sendEmail(subscriber.getEmailAddress(), subject, text);
        });
    }
}
