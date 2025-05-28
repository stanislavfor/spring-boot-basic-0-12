package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Subscriber;
import com.example.taskmanagement.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Subscriber createSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public Subscriber getSubscriberById(Long id) {
        return subscriberRepository.findById(id).orElse(null);
    }

    public Subscriber updateSubscriber(Long id, Subscriber subscriberDetails) {
        Subscriber subscriber = subscriberRepository.findById(id).orElse(null);
        if (subscriber != null) {
            subscriber.setFullName(subscriberDetails.getFullName());
            subscriber.setEmailAddress(subscriberDetails.getEmailAddress());
            return subscriberRepository.save(subscriber);
        }
        return null;
    }

    public void deleteSubscriber(Long id) {
        subscriberRepository.deleteById(id);
    }
}
