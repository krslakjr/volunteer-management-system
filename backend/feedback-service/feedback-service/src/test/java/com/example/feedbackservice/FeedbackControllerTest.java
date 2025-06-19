package com.example.feedbackservice;

import com.example.feedbackservice.controller.FeedbackController;
import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.service.ActivityClientService; 
import com.example.feedbackservice.service.FeedbackService;
import com.example.feedbackservice.service.UserClientService; 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FeedbackService feedbackService;

    @Mock 
    private UserClientService userClientService;

    @Mock 
    private ActivityClientService activityClientService;

    @InjectMocks
    private FeedbackController feedbackController;

    private Feedback feedback;
    private Volunteer volunteer;
    private Activity activity;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();

        volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("Test Volunteer");

        activity = new Activity();
        activity.setActivityId(1L);
        activity.setDescription("Test Activity");

        feedback = new Feedback();
        feedback.setFeedbackId(1L);
        feedback.setRating(5);
        feedback.setComment("Great work!");
        feedback.setTimestamp(new Date());
        feedback.setVolunteer(volunteer);
        feedback.setActivity(activity);
    }

    @Test
    void testGetAllFeedbacks() throws Exception {
        List<Feedback> feedbackList = Arrays.asList(feedback);
        when(feedbackService.getAllFeedbacks()).thenReturn(feedbackList);

        mockMvc.perform(get("/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].feedbackId").value(feedback.getFeedbackId()));

        verify(feedbackService, times(1)).getAllFeedbacks();
    }

    @Test
    void testGetFeedbackById_Found() throws Exception {
        when(feedbackService.getFeedbackById(1L)).thenReturn(Optional.of(feedback));

        mockMvc.perform(get("/feedbacks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feedbackId").value(feedback.getFeedbackId()));

        verify(feedbackService, times(1)).getFeedbackById(1L);
    }

    @Test
    void testGetFeedbackById_NotFound() throws Exception {
        when(feedbackService.getFeedbackById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/feedbacks/1"))
                .andExpect(status().isNotFound());

        verify(feedbackService, times(1)).getFeedbackById(1L);
    }

    @Test
    void testGetFeedbacksByActivityId() throws Exception {
        List<Feedback> feedbackList = Arrays.asList(feedback);
        when(feedbackService.getFeedbacksByActivityId(1L)).thenReturn(feedbackList);

        mockMvc.perform(get("/feedbacks/activity/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].feedbackId").value(feedback.getFeedbackId()));

        verify(feedbackService, times(1)).getFeedbacksByActivityId(1L);
    }

    @Test
    void testGetFeedbacksByVolunteerId() throws Exception {
        List<Feedback> feedbackList = Arrays.asList(feedback);
        when(feedbackService.getFeedbacksByVolunteerId(1L)).thenReturn(feedbackList);

        mockMvc.perform(get("/feedbacks/volunteer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].feedbackId").value(feedback.getFeedbackId()));

        verify(feedbackService, times(1)).getFeedbacksByVolunteerId(1L);
    }

    @Test
    void testDeleteFeedback_Success() throws Exception {
        doNothing().when(feedbackService).deleteFeedback(1L);

        mockMvc.perform(delete("/feedbacks/1"))
                .andExpect(status().isNoContent());

        verify(feedbackService, times(1)).deleteFeedback(1L);
    }

    @Test
    void testDeleteFeedback_Failure() throws Exception {
        doThrow(new RuntimeException("Error deleting")).when(feedbackService).deleteFeedback(1L);

        mockMvc.perform(delete("/feedbacks/1"))
                .andExpect(status().isInternalServerError());

        verify(feedbackService, times(1)).deleteFeedback(1L);
    }
}