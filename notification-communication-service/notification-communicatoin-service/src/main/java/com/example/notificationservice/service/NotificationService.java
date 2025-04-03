package com.example.notificationservice.service;

import com.example.notificationservice.models.Notification;
import com.example.notificationservice.repository.NotificationRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.notificationservice.exception.NotificationNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getAllNotifications(Pageable pageable) {
        Page<Notification> page = notificationRepository.findAll(pageable);
        return page.getContent();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }


    @Transactional
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Transactional
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
                .orElseThrow(() -> new NotificationNotFoundException("Notification with ID " + id + " not found"));
    }

    @Transactional
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotificationNotFoundException("Notification with ID " + id + " not found");
        }
        notificationRepository.deleteById(id);
    }
}