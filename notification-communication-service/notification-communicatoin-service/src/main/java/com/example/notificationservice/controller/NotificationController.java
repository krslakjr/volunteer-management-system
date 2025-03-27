package com.example.notificationservice.controller;

import com.example.notificationservice.exception.NotificationNotFoundException;
import com.example.notificationservice.models.Notification;
import com.example.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Notification createNotification(@Valid @RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @Valid @RequestBody Notification updatedNotification) {
        try {
            Notification notification = notificationService.updateNotification(id, updatedNotification);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
=======
public ResponseEntity<Notification> updateNotification(@PathVariable Long id,@Valid @RequestBody Notification updatedNotification) {
    try {
        Notification notification = notificationService.updateNotification(id, updatedNotification);
        return ResponseEntity.ok(notification);
    } catch (NotificationNotFoundException e) {
        return ResponseEntity.notFound().build();  
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
    }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
