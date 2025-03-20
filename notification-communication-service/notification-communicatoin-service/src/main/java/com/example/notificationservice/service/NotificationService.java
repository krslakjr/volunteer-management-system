package com.example.notificationservice.service;

import com.example.notificationservice.models.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long id, Notification updatedNotification) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.setMessage(updatedNotification.getMessage());
                    notification.setType(updatedNotification.getType());
                    notification.setTimestamp(updatedNotification.getTimestamp());
                    notification.setRead(updatedNotification.isRead());
                    notification.setVolunteer(updatedNotification.getVolunteer());
                    notification.setActivity(updatedNotification.getActivity());
                    notification.setOrganizer(updatedNotification.getOrganizer());
                    return notificationRepository.save(notification);
                })
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
