package com.example.notificationservice;

import com.example.notificationservice.service.MessageService;
import com.example.notificationservice.models.Message;
import com.example.notificationservice.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;

class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(messageRepository);
    }

    @Test
    void testGetAllMessages() {
        // Given
        Message message = new Message();
        message.setMessageId(1L);
        when(messageRepository.findAll()).thenReturn(List.of(message));

        // When
        List<Message> messages = messageService.getAllMessages();

        // Then
        assertEquals(1, messages.size());
        assertEquals(1L, messages.get(0).getMessageId());
    }

    @Test
    void testGetMessageById() {
        // Given
        Message message = new Message();
        message.setMessageId(1L);
        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        // When
        Optional<Message> result = messageService.getMessageById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getMessageId());
    }

    @Test
    void testGetMessageByIdNotFound() {
        // Given
        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<Message> result = messageService.getMessageById(1L);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateMessage() {
        // Given
        Message message = new Message();
        message.setContent("Test content");
        when(messageRepository.save(message)).thenReturn(message);

        // When
        Message createdMessage = messageService.createMessage(message);

        // Then
        assertNotNull(createdMessage);
        assertEquals("Test content", createdMessage.getContent());
    }

    @Test
    void testUpdateMessage() {
        // Given
        Message existingMessage = new Message();
        existingMessage.setMessageId(1L);
        existingMessage.setContent("Old content");

        Message updatedMessage = new Message();
        updatedMessage.setContent("Updated content");

        when(messageRepository.findById(1L)).thenReturn(Optional.of(existingMessage));
        when(messageRepository.save(existingMessage)).thenReturn(existingMessage);

        // When
        Message result = messageService.updateMessage(1L, updatedMessage);

        // Then
        assertEquals("Updated content", result.getContent());
    }

    @Test
    void testUpdateMessageNotFound() {
        // Given
        Message updatedMessage = new Message();
        updatedMessage.setContent("Updated content");

        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> messageService.updateMessage(1L, updatedMessage));
    }

    @Test
    void testDeleteMessage() {
        // Given
        Message message = new Message();
        message.setMessageId(1L);

        // When
        messageService.deleteMessage(1L);

        // Then
        verify(messageRepository, times(1)).deleteById(1L);
    }
}
