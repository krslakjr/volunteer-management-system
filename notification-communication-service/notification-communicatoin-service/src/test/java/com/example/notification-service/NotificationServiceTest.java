package com.example.notificationservice;

import com.example.notificationservice.service.NotificationService;
import com.example.notificationservice.models.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.exception.NotificationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Notification notification;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        notification = new Notification();
        notification.setNotificationId(1L);
        notification.setMessage("Test message");
        notification.setType("Info");
        notification.setRead(false);
    }

    @Test
    void saveNotification_ShouldSaveNotification() {
        when(notificationRepository.save(notification)).thenReturn(notification);

        notificationService.saveNotification(notification);

        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void getAllNotifications_ShouldReturnNotificationsList() {
        Notification notification2 = new Notification();
        notification2.setNotificationId(2L);
        notification2.setMessage("Test message 2");
        notification2.setType("Info");
        notification2.setRead(false);

        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification, notification2));

        List<Notification> notifications = notificationService.getAllNotifications();

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
    }

    @Test
    void getNotificationById_ShouldReturnNotification_WhenExists() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        Optional<Notification> result = notificationService.getNotificationById(1L);

        assertTrue(result.isPresent());
        assertEquals(notification, result.get());
    }

    @Test
    void getNotificationById_ShouldReturnEmpty_WhenNotExists() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Notification> result = notificationService.getNotificationById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void updateNotification_ShouldUpdateNotification_WhenExists() {
        Notification updatedNotification = new Notification();
        updatedNotification.setMessage("Updated message");
        updatedNotification.setType("Updated");
        updatedNotification.setRead(true);

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(notification)).thenReturn(notification);

        Notification result = notificationService.updateNotification(1L, updatedNotification);

        assertNotNull(result);
        assertEquals("Updated message", result.getMessage());
        assertEquals("Updated", result.getType());
        assertTrue(result.isRead());
    }

    @Test
    void updateNotification_ShouldThrowException_WhenNotExists() {
        Notification updatedNotification = new Notification();
        updatedNotification.setMessage("Updated message");

        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        NotificationNotFoundException exception = assertThrows(NotificationNotFoundException.class, () -> {
            notificationService.updateNotification(1L, updatedNotification);
        });

        assertEquals("Notification with ID 1 not found", exception.getMessage());
    }

    @Test
    void deleteNotification_ShouldDeleteNotification_WhenExists() {
        when(notificationRepository.existsById(1L)).thenReturn(true);

        notificationService.deleteNotification(1L);

        verify(notificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotification_ShouldThrowException_WhenNotExists() {
        when(notificationRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotificationNotFoundException.class, () -> notificationService.deleteNotification(1L));
    }
}
