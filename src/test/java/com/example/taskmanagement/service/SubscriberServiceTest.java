package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Subscriber;
import com.example.taskmanagement.repository.SubscriberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;



public class SubscriberServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberService subscriberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSubscribers() {
        Subscriber subscriber1 = new Subscriber();
        subscriber1.setId(1L);
        subscriber1.setFullName("Subscriber 1");
        subscriber1.setEmailAddress("subscriber1@example.com");

        Subscriber subscriber2 = new Subscriber();
        subscriber2.setId(2L);
        subscriber2.setFullName("Subscriber 2");
        subscriber2.setEmailAddress("subscriber2@example.com");

        Mockito.when(subscriberRepository.findAll()).thenReturn(Arrays.asList(subscriber1, subscriber2));

        List<Subscriber> subscribers = subscriberService.getAllSubscribers();
        Assertions.assertEquals(2, subscribers.size());
        Mockito.verify(subscriberRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetSubscriberById() {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(1L);
        subscriber.setFullName("Subscriber 1");
        subscriber.setEmailAddress("subscriber1@example.com");

        Mockito.when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));

        Subscriber foundSubscriber = subscriberService.getSubscriberById(1L);
        Assertions.assertNotNull(foundSubscriber);
        Assertions.assertEquals("Subscriber 1", foundSubscriber.getFullName());
        Mockito.verify(subscriberRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testCreateSubscriber() {
        Subscriber subscriber = new Subscriber();
        subscriber.setFullName("Subscriber 1");
        subscriber.setEmailAddress("subscriber1@example.com");

        Mockito.when(subscriberRepository.save(subscriber)).thenReturn(subscriber);

        Subscriber createdSubscriber = subscriberService.createSubscriber(subscriber);
        Assertions.assertNotNull(createdSubscriber);
        Assertions.assertEquals("Subscriber 1", createdSubscriber.getFullName());
        Mockito.verify(subscriberRepository, Mockito.times(1)).save(subscriber);
    }

    @Test
    public void testUpdateSubscriber() {
        Subscriber existingSubscriber = new Subscriber();
        existingSubscriber.setId(1L);
        existingSubscriber.setFullName("Subscriber 1");
        existingSubscriber.setEmailAddress("subscriber1@example.com");

        Subscriber updatedSubscriberDetails = new Subscriber();
        updatedSubscriberDetails.setFullName("Updated Subscriber");
        updatedSubscriberDetails.setEmailAddress("updated@example.com");

        Mockito.when(subscriberRepository.findById(1L)).thenReturn(Optional.of(existingSubscriber));
        Mockito.when(subscriberRepository.save(existingSubscriber)).thenReturn(existingSubscriber);

        Subscriber updatedSubscriber = subscriberService.updateSubscriber(1L, updatedSubscriberDetails);
        Assertions.assertNotNull(updatedSubscriber);
        Assertions.assertEquals("Updated Subscriber", updatedSubscriber.getFullName());
        Assertions.assertEquals("updated@example.com", updatedSubscriber.getEmailAddress());
        Mockito.verify(subscriberRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(subscriberRepository, Mockito.times(1)).save(existingSubscriber);
    }

    @Test
    public void testDeleteSubscriber() {
        Mockito.doNothing().when(subscriberRepository).deleteById(1L);

        subscriberService.deleteSubscriber(1L);
        Mockito.verify(subscriberRepository, Mockito.times(1)).deleteById(1L);
    }
}
