package com.example.feedbackservice.service;

import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.repository.VolunteerRepository;
import com.example.feedbackservice.repository.ActivityRepository;
import com.example.feedbackservice.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private VolunteerRepository VolunteerRepository;

    @Autowired
    private ActivityRepository ActivityRepository;

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    public List<Feedback> getFeedbacksByActivityId(Long activityId) {
        return feedbackRepository.findByActivityActivityId(activityId);
    }

    public List<Feedback> getFeedbacksByVolunteerId(Long volunteerId) {
        return feedbackRepository.findByVolunteerVolunteerId(volunteerId);
    }

    public Feedback saveOrUpdateFeedback(Feedback feedback) {
        Optional<Volunteer> volunteer = VolunteerRepository.findById(feedback.getVolunteer().getVolunteerId());
        Optional<Activity> activity = ActivityRepository.findById(feedback.getActivity().getActivityId());

        if (volunteer.isPresent() && activity.isPresent()) {
            feedback.setVolunteer(volunteer.get());
            feedback.setActivity(activity.get());
            return feedbackRepository.save(feedback);
        } else {
            throw new RuntimeException("Invalid Volunteer or Activity ID");
        }
    }

    public void deleteFeedback(Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
        } else {
            throw new RuntimeException("Feedback not found with id " + id);
        }
    }
}
