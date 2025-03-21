package com.example.feedbackservice;

import com.example.feedbackservice.models.Activity;
import com.example.feedbackservice.models.Feedback;
import com.example.feedbackservice.models.ActivityStatistics;
import com.example.feedbackservice.models.Volunteer;
import com.example.feedbackservice.repository.ActivityRepository;
import com.example.feedbackservice.repository.FeedbackRepository;
import com.example.feedbackservice.repository.ActivityStatisticsRepository;
import com.example.feedbackservice.repository.VolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ActivityRepository activityRepository;
    private final FeedbackRepository feedbackRepository;
    private final ActivityStatisticsRepository activityStatisticsRepository;
    private final VolunteerRepository volunteerRepository;

    public DataInitializer(ActivityRepository activityRepository,
                          FeedbackRepository feedbackRepository,
                          ActivityStatisticsRepository activityStatisticsRepository,
                          VolunteerRepository volunteerRepository) {
        this.activityRepository = activityRepository;
        this.feedbackRepository = feedbackRepository;
        this.activityStatisticsRepository = activityStatisticsRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public void run(String... args) {
        // Kreiranje volontera
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setName("Ana");
        volunteer1.setContactInfo("ana@email.com");
        volunteerRepository.save(volunteer1);

        // Kreiranje aktivnosti
        Activity activity1 = new Activity();
        activity1.setDescription("Pomoć starijim osobama");
        activity1.setDate(new Date());
        activity1.setLocation("Dom za stare");
        activity1.setVolunteersNeeded(15);
        activityRepository.save(activity1);

        // Kreiranje feedbacka za aktivnost
        Feedback feedback1 = new Feedback();
        feedback1.setActivity(activity1);
        feedback1.setVolunteer(volunteer1);
        feedback1.setRating(4);
        feedback1.setComment("Pomoć bila izuzetno korisna.");
        feedback1.setTimestamp(new Date());
        feedbackRepository.save(feedback1);

        // Kreiranje statistike za aktivnost
        ActivityStatistics activityStatistics1 = new ActivityStatistics();
        activityStatistics1.setActivity(activity1);
        activityStatistics1.setTotalRatings(1);
        activityStatistics1.setTotalComments(1);
        activityStatistics1.setAverageRating(4.0);
        activityStatisticsRepository.save(activityStatistics1);

        log.info("-------------------------------");
        log.info("Uneseni podaci u bazu:");
        log.info("Volonter: " + volunteer1.getName());
        log.info("Aktivnost: " + activity1.getDescription());
        log.info("Feedback: " + feedback1.getComment() + " - Rating: " + feedback1.getRating());
        log.info("Statistika aktivnosti: " + activityStatistics1.getTotalRatings() + " ratings - " + activityStatistics1.getTotalComments() + " comments - Prosječan rating: " + activityStatistics1.getAverageRating());
        log.info("-------------------------------");
    }
}
