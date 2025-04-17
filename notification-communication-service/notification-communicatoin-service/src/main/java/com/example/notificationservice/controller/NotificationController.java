package com.example.notificationservice.controller;

import com.example.notificationservice.exception.NotificationNotFoundException;
import com.example.notificationservice.exception.ResourceNotFoundException;
import com.example.notificationservice.models.Notification;
import com.example.notificationservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public List<Notification> getAllNotifications(@RequestParam(required = false) Integer page, 
                                            @RequestParam(required = false) Integer size) {
        Pageable pageable = Pageable.unpaged();
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size);
        }
        return notificationService.getAllNotifications(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
       Notification notification = notificationService.getNotificationById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with ID " + id + " not found"));
        return ResponseEntity.ok(notification);
    }

    @PostMapping
    public Notification createNotification(@Valid @RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @Valid @RequestBody Notification updatedNotification) {
        Notification notification = notificationService.updateNotification(id, updatedNotification);
        return ResponseEntity.ok(notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}