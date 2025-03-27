package com.example.notificationservice;

import com.example.notificationservice.controller.EngagementStatisticsController;
import com.example.notificationservice.models.EngagementStatistics;
import com.example.notificationservice.models.Volunteer;
import com.example.notificationservice.service.EngagementStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EngagementStatisticsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EngagementStatisticsService engagementStatisticsService;

    @InjectMocks
    private EngagementStatisticsController engagementStatisticsController;

    private EngagementStatistics statistics;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(engagementStatisticsController).build();

        Volunteer volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);

        statistics = new EngagementStatistics();
        statistics.setId(1L);
        statistics.setVolunteer(volunteer);
        statistics.setTotalActivities(10);
        statistics.setMessagesSent(50);
        statistics.setForumPostsMade(20);
        statistics.setNotificationsReceived(100);
    }

    @Test
    void testGetAllStatistics() throws Exception {
        when(engagementStatisticsService.getAllStatistics()).thenReturn(List.of(statistics));

        mockMvc.perform(get("/engagement-statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(statistics.getId()))
                .andExpect(jsonPath("$[0].totalActivities").value(statistics.getTotalActivities()))
                .andExpect(jsonPath("$[0].messagesSent").value(statistics.getMessagesSent()))
                .andExpect(jsonPath("$[0].forumPostsMade").value(statistics.getForumPostsMade()))
                .andExpect(jsonPath("$[0].notificationsReceived").value(statistics.getNotificationsReceived()));

        verify(engagementStatisticsService, times(1)).getAllStatistics();
    }

    @Test
    void testGetStatisticsById_Found() throws Exception {
        when(engagementStatisticsService.getStatisticsById(any(Long.class))).thenReturn(Optional.of(statistics));

        mockMvc.perform(get("/engagement-statistics/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(statistics.getId()))
                .andExpect(jsonPath("$.totalActivities").value(statistics.getTotalActivities()))
                .andExpect(jsonPath("$.messagesSent").value(statistics.getMessagesSent()))
                .andExpect(jsonPath("$.forumPostsMade").value(statistics.getForumPostsMade()))
                .andExpect(jsonPath("$.notificationsReceived").value(statistics.getNotificationsReceived()));

        verify(engagementStatisticsService, times(1)).getStatisticsById(1L);
    }

    @Test
    void testGetStatisticsById_NotFound() throws Exception {
        when(engagementStatisticsService.getStatisticsById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/engagement-statistics/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(engagementStatisticsService, times(1)).getStatisticsById(1L);
    }

    @Test
    void testCreateStatistics() throws Exception {
        when(engagementStatisticsService.createStatistics(any(EngagementStatistics.class))).thenReturn(statistics);

        mockMvc.perform(post("/engagement-statistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"volunteer\": {\"id\": 1}," +
                                "\"totalActivities\": 10," +
                                "\"messagesSent\": 50," +
                                "\"forumPostsMade\": 20," +
                                "\"notificationsReceived\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(statistics.getId()))
                .andExpect(jsonPath("$.totalActivities").value(statistics.getTotalActivities()))
                .andExpect(jsonPath("$.messagesSent").value(statistics.getMessagesSent()))
                .andExpect(jsonPath("$.forumPostsMade").value(statistics.getForumPostsMade()))
                .andExpect(jsonPath("$.notificationsReceived").value(statistics.getNotificationsReceived()));

        verify(engagementStatisticsService, times(1)).createStatistics(any(EngagementStatistics.class));
    }
}
