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
import java.util.Calendar; // Za lakše manipulisanje datumima

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
        // Clear existing data (optional, for development purposes, be careful in production!)
        feedbackRepository.deleteAll();
        activityStatisticsRepository.deleteAll();
        activityRepository.deleteAll();
        volunteerRepository.deleteAll();

        // --- Create Volunteers ---
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setName("Alice Johnson");
        volunteer1.setContactInfo("alice.j@example.com");
        volunteerRepository.save(volunteer1);

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setName("Bob Williams");
        volunteer2.setContactInfo("bob.w@example.com");
        volunteerRepository.save(volunteer2);

        Volunteer volunteer3 = new Volunteer();
        volunteer3.setName("Charlie Davis");
        volunteer3.setContactInfo("charlie.d@example.com");
        volunteerRepository.save(volunteer3);

        Volunteer volunteer4 = new Volunteer();
        volunteer4.setName("Diana Miller");
        volunteer4.setContactInfo("diana.m@example.com");
        volunteerRepository.save(volunteer4);

        Volunteer volunteer5 = new Volunteer();
        volunteer5.setName("Eve Wilson");
        volunteer5.setContactInfo("eve.w@example.com");
        volunteerRepository.save(volunteer5);

        log.info("--- Volunteers Created ---");
        volunteerRepository.findAll().forEach(v -> log.info("  - " + v.getName()));

        // --- Create Activities (Events) ---

        // Past Activity 1: Food Bank Distribution (Completed)
        Activity activityPast1 = new Activity();
        activityPast1.setDescription("Food Bank Distribution");
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.MONTH, -2); // 2 months ago
        cal1.add(Calendar.DAY_OF_MONTH, -10); // 10 days before that
        activityPast1.setDate(cal1.getTime());
        activityPast1.setLocation("Community Food Bank");
        activityPast1.setVolunteersNeeded(10);
        activityRepository.save(activityPast1);

        // Past Activity 2: Local Park Cleanup (Completed)
        Activity activityPast2 = new Activity();
        activityPast2.setDescription("Local Park Cleanup");
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MONTH, -1); // 1 month ago
        cal2.add(Calendar.DAY_OF_MONTH, -5); // 5 days before that
        activityPast2.setDate(cal2.getTime());
        activityPast2.setLocation("Green Oasis Park");
        activityPast2.setVolunteersNeeded(8);
        activityRepository.save(activityPast2);

        // Past Activity 3: Animal Shelter Aid (Completed)
        Activity activityPast3 = new Activity();
        activityPast3.setDescription("Animal Shelter Aid");
        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.DAY_OF_MONTH, -15); // 15 days ago
        activityPast3.setDate(cal3.getTime());
        activityPast3.setLocation("Happy Paws Shelter");
        activityPast3.setVolunteersNeeded(7);
        activityRepository.save(activityPast3);


        // Future Activity 1: Community Garden Project (Upcoming)
        Activity activityFuture1 = new Activity();
        activityFuture1.setDescription("Community Garden Project");
        Calendar calFuture1 = Calendar.getInstance();
        calFuture1.add(Calendar.DAY_OF_MONTH, 5); // 5 days from now
        activityFuture1.setDate(calFuture1.getTime());
        activityFuture1.setLocation("Sunshine Community Gardens");
        activityFuture1.setVolunteersNeeded(12);
        activityRepository.save(activityFuture1);

        // Future Activity 2: Elderly Care Home Visit (Upcoming)
        Activity activityFuture2 = new Activity();
        activityFuture2.setDescription("Elderly Care Home Visit");
        Calendar calFuture2 = Calendar.getInstance();
        calFuture2.add(Calendar.WEEK_OF_YEAR, 2); // 2 weeks from now
        activityFuture2.setDate(calFuture2.getTime());
        activityFuture2.setLocation("Golden Years Care Home");
        activityFuture2.setVolunteersNeeded(5);
        activityRepository.save(activityFuture2);

        // Future Activity 3: Beach Cleanup Initiative (Upcoming)
        Activity activityFuture3 = new Activity();
        activityFuture3.setDescription("Beach Cleanup Initiative");
        Calendar calFuture3 = Calendar.getInstance();
        calFuture3.add(Calendar.MONTH, 1); // 1 month from now
        activityFuture3.setDate(calFuture3.getTime());
        activityFuture3.setLocation("Crystal Sands Beach");
        activityFuture3.setVolunteersNeeded(15);
        activityRepository.save(activityFuture3);

        log.info("--- Activities (Events) Created ---");
        activityRepository.findAll().forEach(a -> log.info("  - " + a.getDescription() + " on " + a.getDate()));


        // --- Create Feedback & ActivityStatistics for PAST activities ---

        // Feedback for activityPast1
        Feedback feedbackP1V1 = new Feedback(); // Koristimo no-argument konstruktor
        feedbackP1V1.setActivity(activityPast1);
        feedbackP1V1.setVolunteer(volunteer1);
        feedbackP1V1.setRating(5);
        feedbackP1V1.setComment("Excellent organization, truly made a difference!");
        feedbackP1V1.setTimestamp(new Date(cal1.getTimeInMillis() + (1000L * 60 * 60 * 24 * 2))); // 2 days after event
        feedbackRepository.save(feedbackP1V1);

        Feedback feedbackP1V2 = new Feedback(); // Koristimo no-argument konstruktor
        feedbackP1V2.setActivity(activityPast1);
        feedbackP1V2.setVolunteer(volunteer2);
        feedbackP1V2.setRating(4);
        feedbackP1V2.setComment("Good initiative, a bit chaotic but productive.");
        feedbackP1V2.setTimestamp(new Date(cal1.getTimeInMillis() + (1000L * 60 * 60 * 24 * 3))); // 3 days after event
        feedbackRepository.save(feedbackP1V2);

        ActivityStatistics statsPast1 = new ActivityStatistics(); // Koristimo no-argument konstruktor
        statsPast1.setActivity(activityPast1);
        statsPast1.setTotalRatings(2);
        statsPast1.setTotalComments(2);
        statsPast1.setAverageRating(4.5);
        activityStatisticsRepository.save(statsPast1);


        // Feedback for activityPast2
        Feedback feedbackP2V3 = new Feedback(); // Koristimo no-argument konstruktor
        feedbackP2V3.setActivity(activityPast2);
        feedbackP2V3.setVolunteer(volunteer3);
        feedbackP2V3.setRating(5);
        feedbackP2V3.setComment("The park looks amazing now! Great team work.");
        feedbackP2V3.setTimestamp(new Date(cal2.getTimeInMillis() + (1000L * 60 * 60 * 24 * 1))); // 1 day after event
        feedbackRepository.save(feedbackP2V3);

        Feedback feedbackP2V4 = new Feedback(); // Koristimo no-argument konstruktor
        feedbackP2V4.setActivity(activityPast2);
        feedbackP2V4.setVolunteer(volunteer4);
        feedbackP2V4.setRating(4);
        feedbackP2V4.setComment("Enjoyed it, could use more tools next time.");
        feedbackP2V4.setTimestamp(new Date(cal2.getTimeInMillis() + (1000L * 60 * 60 * 24 * 2))); // 2 days after event
        feedbackRepository.save(feedbackP2V4);

        ActivityStatistics statsPast2 = new ActivityStatistics(); // Koristimo no-argument konstruktor
        statsPast2.setActivity(activityPast2);
        statsPast2.setTotalRatings(2);
        statsPast2.setTotalComments(2);
        statsPast2.setAverageRating(4.5);
        activityStatisticsRepository.save(statsPast2);

        // Feedback for activityPast3
        Feedback feedbackP3V5 = new Feedback(); // Koristimo no-argument konstruktor
        feedbackP3V5.setActivity(activityPast3);
        feedbackP3V5.setVolunteer(volunteer5);
        feedbackP3V5.setRating(3);
        feedbackP3V5.setComment("Sad to see so many abandoned animals, but glad I could help.");
        feedbackP3V5.setTimestamp(new Date(cal3.getTimeInMillis() + (1000L * 60 * 60 * 24 * 3))); // 3 days after event
        feedbackRepository.save(feedbackP3V5);

        ActivityStatistics statsPast3 = new ActivityStatistics(); // Koristimo no-argument konstruktor
        statsPast3.setActivity(activityPast3);
        statsPast3.setTotalRatings(1);
        statsPast3.setTotalComments(1);
        statsPast3.setAverageRating(3.0);
        activityStatisticsRepository.save(statsPast3);


        log.info("--- Feedback and Statistics Created for Past Activities ---");
        feedbackRepository.findAll().forEach(f -> log.info("  - Feedback for '" + f.getActivity().getDescription() + "' by " + f.getVolunteer().getName() + ": " + f.getRating() + " stars"));
        activityStatisticsRepository.findAll().forEach(s -> log.info("  - Stats for '" + s.getActivity().getDescription() + "': Avg Rating " + s.getAverageRating()));

        log.info("-------------------------------");
        log.info("Database initialization complete with sample data in English.");
        log.info("Total Activities (Events): " + activityRepository.count());
        log.info("Total Volunteers: " + volunteerRepository.count());
        log.info("Total Feedback entries: " + feedbackRepository.count());
        log.info("Total Activity Statistics entries: " + activityStatisticsRepository.count());
        log.info("-------------------------------");
    }
}