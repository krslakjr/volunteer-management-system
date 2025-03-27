package com.example.notificationservice;

import com.example.notificationservice.controller.MessageController;
import com.example.notificationservice.models.Message;
import com.example.notificationservice.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void getAllMessages_ShouldReturnListOfMessages() throws Exception {
        Message message1 = new Message();
        message1.setMessageId(1L);
        message1.setContent("Hello");
        message1.setTimestamp(new Date());

        Message message2 = new Message();
        message2.setMessageId(2L);
        message2.setContent("Hi");
        message2.setTimestamp(new Date());

        when(messageService.getAllMessages()).thenReturn(Arrays.asList(message1, message2));

        mockMvc.perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void getMessageById_ShouldReturnMessage_WhenMessageExists() throws Exception {
        Message message = new Message();
        message.setMessageId(1L);
        message.setContent("Hello");
        message.setTimestamp(new Date());

        when(messageService.getMessageById(1L)).thenReturn(Optional.of(message));

        mockMvc.perform(get("/messages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value(1));
    }

    @Test
    void getMessageById_ShouldReturnNotFound_WhenMessageDoesNotExist() throws Exception {
        when(messageService.getMessageById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/messages/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMessage_ShouldReturnCreatedMessage() throws Exception {
        Message message = new Message();
        message.setMessageId(1L);
        message.setContent("Hello");
        message.setTimestamp(new Date());

        when(messageService.createMessage(any(Message.class))).thenReturn(message);

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Hello\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello"));
    }

    @Test
    void updateMessage_ShouldReturnUpdatedMessage_WhenMessageExists() throws Exception {
        Message message = new Message();
        message.setMessageId(1L);
        message.setContent("Updated content");
        message.setTimestamp(new Date());

        when(messageService.updateMessage(eq(1L), any(Message.class))).thenReturn(message);

        mockMvc.perform(put("/messages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Updated content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    void updateMessage_ShouldReturnNotFound_WhenMessageDoesNotExist() throws Exception {
        when(messageService.updateMessage(eq(1L), any(Message.class))).thenThrow(new RuntimeException("Message not found"));

        mockMvc.perform(put("/messages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Updated content\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMessage_ShouldReturnNoContent() throws Exception {
        doNothing().when(messageService).deleteMessage(1L);

        mockMvc.perform(delete("/messages/1"))
                .andExpect(status().isNoContent());
    }
}
