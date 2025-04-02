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
        Message message = new Message();
        message.setMessageId(1L);
        when(messageRepository.findAll()).thenReturn(List.of(message));

        List<Message> messages = messageService.getAllMessages();

        assertEquals(1, messages.size());
        assertEquals(1L, messages.get(0).getMessageId());
    }

    @Test
    void testGetMessageById() {
        Message message = new Message();
        message.setMessageId(1L);
        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        Optional<Message> result = messageService.getMessageById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getMessageId());
    }

    @Test
    void testGetMessageByIdNotFound() {
        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Message> result = messageService.getMessageById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateMessage() {
        Message message = new Message();
        message.setContent("Test content");
        when(messageRepository.save(message)).thenReturn(message);

        Message createdMessage = messageService.createMessage(message);

        assertNotNull(createdMessage);
        assertEquals("Test content", createdMessage.getContent());
    }

    @Test
    void testUpdateMessage() {
        Message existingMessage = new Message();
        existingMessage.setMessageId(1L);
        existingMessage.setContent("Old content");

        Message updatedMessage = new Message();
        updatedMessage.setContent("Updated content");

        when(messageRepository.findById(1L)).thenReturn(Optional.of(existingMessage));
        when(messageRepository.save(existingMessage)).thenReturn(existingMessage);

        Message result = messageService.updateMessage(1L, updatedMessage);

        assertEquals("Updated content", result.getContent());
    }

    @Test
    void testUpdateMessageNotFound() {
        Message updatedMessage = new Message();
        updatedMessage.setContent("Updated content");

        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> messageService.updateMessage(1L, updatedMessage));
    }

    @Test
void testDeleteMessage() {
    Message message = new Message();
    message.setMessageId(1L);

    when(messageRepository.findById(1L)).thenReturn(Optional.of(message));
    messageService.deleteMessage(1L);

    verify(messageRepository, times(1)).deleteById(1L); 
}

}
