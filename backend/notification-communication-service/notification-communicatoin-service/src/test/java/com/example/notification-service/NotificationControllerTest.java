package com.example.notificationservice;

import com.example.notificationservice.exception.NotificationNotFoundException;
import com.example.notificationservice.controller.NotificationController;
import com.example.notificationservice.models.Notification;
import com.example.notificationservice.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        notification.setNotificationId(1L);
        notification.setMessage("Test message");
        notification.setType("INFO");
        notification.setRead(false);
    }

    @Test
    void testGetNotificationById() throws Exception {
        when(notificationService.getNotificationById(1L)).thenReturn(notification);

        mockMvc.perform(get("/notifications/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Test message"));
    }

    @Test
    void testGetNotificationByIdNotFound() throws Exception {
        when(notificationService.getNotificationById(1L)).thenReturn(null);

        mockMvc.perform(get("/notifications/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateNotification() throws Exception {
        
        when(notificationService.createNotification(Mockito.any(Notification.class))).thenReturn(notification);

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Test message"));
    }



    @Test
    void testDeleteNotification() throws Exception {
        doNothing().when(notificationService).deleteNotification(1L);

        mockMvc.perform(delete("/notifications/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
