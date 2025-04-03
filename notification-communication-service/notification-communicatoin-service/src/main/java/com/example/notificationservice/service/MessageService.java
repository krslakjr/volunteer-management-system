package com.example.notificationservice.service;

import com.example.notificationservice.exception.ResourceNotFoundException;
import com.example.notificationservice.models.Message;
import com.example.notificationservice.repository.MessageRepository;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    
    public List<Message> getAllMessages(Pageable pageable) {
        Page<Message> page = messageRepository.findAll(pageable);
        return page.getContent();
    }

   
    public void saveMessage(Message message) {
        try {
            messageRepository.save(message);
        } catch (Exception e) {
          
            throw new RuntimeException("Error saving message", e);
        }
    }

   
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }


    @Transactional
    public Message createMessage(Message message) {
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
         
            throw new RuntimeException("Error creating message", e);
        }
    }

    @Transactional
    public Message updateMessage(Long id, Message updatedMessage) {
        Optional<Message> existingMessage = messageRepository.findById(id);
        
     
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setContent(updatedMessage.getContent());
            message.setTimestamp(updatedMessage.getTimestamp());
            message.setSender(updatedMessage.getSender());
            message.setReceiver(updatedMessage.getReceiver());
            message.setOrganizer(updatedMessage.getOrganizer());
            return messageRepository.save(message);
        } else {
            
            throw new ResourceNotFoundException("Message not found with id " + id, "id");
        }
    }

    
    @Transactional
    public void deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        
        
        if (message.isPresent()) {
            messageRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Message not found with id " + id, "id");
        }
    }
}