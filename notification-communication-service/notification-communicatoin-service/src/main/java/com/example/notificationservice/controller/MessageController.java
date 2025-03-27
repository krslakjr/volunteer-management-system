package com.example.notificationservice.controller;

import com.example.notificationservice.models.Message;
import com.example.notificationservice.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Message createMessage(@Valid @RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @Valid @RequestBody Message updatedMessage) {
=======
    public ResponseEntity<Message> updateMessage(@PathVariable Long id,@Valid @RequestBody Message updatedMessage) {
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
        try {
            Message message = messageService.updateMessage(id, updatedMessage);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
