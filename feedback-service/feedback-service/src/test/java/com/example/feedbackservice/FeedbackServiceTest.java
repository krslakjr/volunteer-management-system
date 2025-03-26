package com.example.feedbackservice;

import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.repository.ActivityRepository;
import com.example.feedbackservice.repository.FeedbackRepository;
import com.example.feedbackservice.repository.VolunteerRepository;
import com.example.feedbackservice.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    private Feedback feedback;
    private Volunteer volunteer;
    private Activity activity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        volunteer = new Volunteer();
        volunteer.setVolunteerId(1L);
        volunteer.setName("John Doe");

        activity = new Activity();
        activity.setActivityId(1L);

        feedback = new Feedback();
        feedback.setFeedbackId(1L);
        feedback.setVolunteer(volunteer);
        feedback.setActivity(activity);
        feedback.setRating(5);
        feedback.setComment("Great work!");
    }

    @Test
    void testSaveOrUpdateFeedback_Success() {
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.of(volunteer));
        when(activityRepository.findById(any(Long.class))).thenReturn(Optional.of(activity));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback savedFeedback = feedbackService.saveOrUpdateFeedback(feedback);

        assertNotNull(savedFeedback);
        assertEquals(feedback.getFeedbackId(), savedFeedback.getFeedbackId());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testSaveOrUpdateFeedback_InvalidVolunteerOrActivity() {
        when(volunteerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(activityRepository.findById(any(Long.class))).thenReturn(Optional.of(activity));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            feedbackService.saveOrUpdateFeedback(feedback);
        });

        assertEquals("Invalid Volunteer or Activity ID", thrown.getMessage());
    }

    @Test
    void testDeleteFeedback_Success() {
        when(feedbackRepository.existsById(any(Long.class))).thenReturn(true);

        feedbackService.deleteFeedback(1L);

        verify(feedbackRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void testDeleteFeedback_NotFound() {
        when(feedbackRepository.existsById(any(Long.class))).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            feedbackService.deleteFeedback(1L);
        });

        assertEquals("Feedback not found with id 1", thrown.getMessage());
    }

    @Test
    void testGetFeedbackById() {
        when(feedbackRepository.findById(any(Long.class))).thenReturn(Optional.of(feedback));

        Optional<Feedback> foundFeedback = feedbackService.getFeedbackById(1L);

        assertTrue(foundFeedback.isPresent());
        assertEquals(feedback.getFeedbackId(), foundFeedback.get().getFeedbackId());
    }

    @Test
    void testGetAllFeedbacks() {
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback));

        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

        assertEquals(1, feedbacks.size());
        assertEquals(feedback.getFeedbackId(), feedbacks.get(0).getFeedbackId());
    }
}
