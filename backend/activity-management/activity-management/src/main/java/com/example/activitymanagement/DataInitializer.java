package com.example.activitymanagement;

import com.example.activitymanagement.models.Activity;
import com.example.activitymanagement.models.Volunteer;
import com.example.activitymanagement.models.ActivityVolunteer;
import com.example.activitymanagement.models.Team;
import com.example.activitymanagement.models.TeamActivity;
import com.example.activitymanagement.repository.ActivityRepository;
import com.example.activitymanagement.repository.VolunteerRepository;
import com.example.activitymanagement.repository.ActivityVolunteerRepository;
import com.example.activitymanagement.repository.TeamRepository;
import com.example.activitymanagement.repository.TeamActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ActivityRepository activityRepository;
    private final VolunteerRepository volunteerRepository;
    private final ActivityVolunteerRepository activityVolunteerRepository;
    private final TeamRepository teamRepository;
    private final TeamActivityRepository teamActivityRepository;

    public DataInitializer(ActivityRepository activityRepository,
                           VolunteerRepository volunteerRepository,
                           ActivityVolunteerRepository activityVolunteerRepository,
                           TeamRepository teamRepository,
                           TeamActivityRepository teamActivityRepository) {
        this.activityRepository = activityRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityVolunteerRepository = activityVolunteerRepository;
        this.teamRepository = teamRepository;
        this.teamActivityRepository = teamActivityRepository;
    }

    @Override
    public void run(String... args) {
        // Clear existing data (optional, for development purposes)
        teamActivityRepository.deleteAll();
        activityVolunteerRepository.deleteAll();
        activityRepository.deleteAll();
        volunteerRepository.deleteAll();
        teamRepository.deleteAll();


        // Create Activities
        // Past Activities
        Activity activityPast1 = new Activity();
        activityPast1.setDescription("Winter Coat Drive");
        activityPast1.setDate("2024-12-10");
        activityPast1.setLocation("Community Hall");
        activityPast1.setVolunteersNeeded(5);
        activityPast1.setAvailableSpots(0); // Assuming it's completed
        activityRepository.save(activityPast1);

        Activity activityPast2 = new Activity();
        activityPast2.setDescription("Soup Kitchen Service");
        activityPast2.setDate("2025-01-22");
        activityPast2.setLocation("Downtown Mission");
        activityPast2.setVolunteersNeeded(7);
        activityPast2.setAvailableSpots(0); // Assuming it's completed
        activityRepository.save(activityPast2);

        Activity activityPast3 = new Activity();
        activityPast3.setDescription("Park Bench Restoration");
        activityPast3.setDate("2025-03-05");
        activityPast3.setLocation("Riverside Park");
        activityPast3.setVolunteersNeeded(6);
        activityPast3.setAvailableSpots(0); // Assuming it's completed
        activityRepository.save(activityPast3);


        // Future Activities
        Activity activity1 = new Activity();
        activity1.setDescription("Community Garden Cleanup");
        activity1.setDate("2025-07-15");
        activity1.setLocation("Central Park Gardens");
        activity1.setVolunteersNeeded(8);
        activity1.setAvailableSpots(activity1.getVolunteersNeeded());
        activityRepository.save(activity1);

        Activity activity2 = new Activity();
        activity2.setDescription("Homeless Shelter Meal Prep");
        activity2.setDate("2025-07-20");
        activity2.setLocation("City Outreach Center");
        activity2.setVolunteersNeeded(5);
        activity2.setAvailableSpots(activity2.getVolunteersNeeded());
        activityRepository.save(activity2);

        Activity activity3 = new Activity();
        activity3.setDescription("Beach Cleanup Drive");
        activity3.setDate("2025-08-01");
        activity3.setLocation("Sunny Coast Beach");
        activity3.setVolunteersNeeded(12);
        activity3.setAvailableSpots(activity3.getVolunteersNeeded());
        activityRepository.save(activity3);

        Activity activity4 = new Activity();
        activity4.setDescription("Animal Shelter Assistance");
        activity4.setDate("2025-08-10");
        activity4.setLocation("Happy Tails Animal Shelter");
        activity4.setVolunteersNeeded(7);
        activity4.setAvailableSpots(activity4.getVolunteersNeeded());
        activityRepository.save(activity4);

        Activity activity5 = new Activity();
        activity5.setDescription("Elderly Home Visiting");
        activity5.setDate("2025-08-25");
        activity5.setLocation("Golden Years Retirement Home");
        activity5.setVolunteersNeeded(6);
        activity5.setAvailableSpots(activity5.getVolunteersNeeded());
        activityRepository.save(activity5);

        Activity activity6 = new Activity();
        activity6.setDescription("Environmental Awareness Workshop");
        activity6.setDate("2025-09-05");
        activity6.setLocation("Public Library Auditorium");
        activity6.setVolunteersNeeded(4);
        activity6.setAvailableSpots(activity6.getVolunteersNeeded());
        activityRepository.save(activity6);

        // Create Volunteers
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setName("Alice Smith");
        volunteer1.setContactInfo("alice.smith@example.com");
        volunteerRepository.save(volunteer1);

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setName("Bob Johnson");
        volunteer2.setContactInfo("bob.j@example.com");
        volunteerRepository.save(volunteer2);

        Volunteer volunteer3 = new Volunteer();
        volunteer3.setName("Charlie Brown");
        volunteer3.setContactInfo("charlie.b@example.com");
        volunteerRepository.save(volunteer3);

        Volunteer volunteer4 = new Volunteer();
        volunteer4.setName("Diana Prince");
        volunteer4.setContactInfo("diana.p@example.com");
        volunteerRepository.save(volunteer4);

        Volunteer volunteer5 = new Volunteer();
        volunteer5.setName("Ethan Hunt");
        volunteer5.setContactInfo("ethan.h@example.com");
        volunteerRepository.save(volunteer5);

        Volunteer volunteer6 = new Volunteer();
        volunteer6.setName("Fiona Gallagher");
        volunteer6.setContactInfo("fiona.g@example.com");
        volunteerRepository.save(volunteer6);

        Volunteer volunteer7 = new Volunteer();
        volunteer7.setName("George Costanza");
        volunteer7.setContactInfo("george.c@example.com");
        volunteerRepository.save(volunteer7);

        Volunteer volunteer8 = new Volunteer();
        volunteer8.setName("Hannah Montana");
        volunteer8.setContactInfo("hannah.m@example.com");
        volunteerRepository.save(volunteer8);

        Volunteer volunteer9 = new Volunteer();
        volunteer9.setName("Ivan Drago");
        volunteer9.setContactInfo("ivan.d@example.com");
        volunteerRepository.save(volunteer9);


        // Link Volunteers to Activities (ActivityVolunteer)
        // For past activities
        ActivityVolunteer avPast1 = new ActivityVolunteer();
        avPast1.setActivity(activityPast1);
        avPast1.setVolunteer(volunteer1);
        activityVolunteerRepository.save(avPast1);

        ActivityVolunteer avPast2 = new ActivityVolunteer();
        avPast2.setActivity(activityPast1);
        avPast2.setVolunteer(volunteer2);
        activityVolunteerRepository.save(avPast2);

        ActivityVolunteer avPast3 = new ActivityVolunteer();
        avPast3.setActivity(activityPast2);
        avPast3.setVolunteer(volunteer3);
        activityVolunteerRepository.save(avPast3);

        ActivityVolunteer avPast4 = new ActivityVolunteer();
        avPast4.setActivity(activityPast3);
        avPast4.setVolunteer(volunteer4);
        activityVolunteerRepository.save(avPast4);


        // For future activities
        ActivityVolunteer av1 = new ActivityVolunteer();
        av1.setActivity(activity1);
        av1.setVolunteer(volunteer1);
        activityVolunteerRepository.save(av1);
        activity1.setAvailableSpots(activity1.getAvailableSpots() - 1);
        activityRepository.save(activity1);

        ActivityVolunteer av2 = new ActivityVolunteer();
        av2.setActivity(activity1);
        av2.setVolunteer(volunteer2);
        activityVolunteerRepository.save(av2);
        activity1.setAvailableSpots(activity1.getAvailableSpots() - 1);
        activityRepository.save(activity1);

        ActivityVolunteer av3 = new ActivityVolunteer();
        av3.setActivity(activity2);
        av3.setVolunteer(volunteer3);
        activityVolunteerRepository.save(av3);
        activity2.setAvailableSpots(activity2.getAvailableSpots() - 1);
        activityRepository.save(activity2);

        ActivityVolunteer av4 = new ActivityVolunteer();
        av4.setActivity(activity3);
        av4.setVolunteer(volunteer4);
        activityVolunteerRepository.save(av4);
        activity3.setAvailableSpots(activity3.getAvailableSpots() - 1);
        activityRepository.save(activity3);

        ActivityVolunteer av5 = new ActivityVolunteer();
        av5.setActivity(activity4);
        av5.setVolunteer(volunteer5);
        activityVolunteerRepository.save(av5);
        activity4.setAvailableSpots(activity4.getAvailableSpots() - 1);
        activityRepository.save(activity4);

        ActivityVolunteer av6 = new ActivityVolunteer();
        av6.setActivity(activity5);
        av6.setVolunteer(volunteer6);
        activityVolunteerRepository.save(av6);
        activity5.setAvailableSpots(activity5.getAvailableSpots() - 1);
        activityRepository.save(activity5);

        ActivityVolunteer av7 = new ActivityVolunteer();
        av7.setActivity(activity1);
        av7.setVolunteer(volunteer7);
        activityVolunteerRepository.save(av7);
        activity1.setAvailableSpots(activity1.getAvailableSpots() - 1);
        activityRepository.save(activity1);

        ActivityVolunteer av8 = new ActivityVolunteer();
        av8.setActivity(activity2);
        av8.setVolunteer(volunteer8);
        activityVolunteerRepository.save(av8);
        activity2.setAvailableSpots(activity2.getAvailableSpots() - 1);
        activityRepository.save(activity2);

        ActivityVolunteer av9 = new ActivityVolunteer();
        av9.setActivity(activity3);
        av9.setVolunteer(volunteer9);
        activityVolunteerRepository.save(av9);
        activity3.setAvailableSpots(activity3.getAvailableSpots() - 1);
        activityRepository.save(activity3);

        // Create Teams
        Team team1 = new Team();
        team1.setTeamName("Green Guardians");
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setTeamName("Helping Hands Crew");
        teamRepository.save(team2);

        Team team3 = new Team();
        team3.setTeamName("Community Champions");
        teamRepository.save(team3);

        Team team4 = new Team();
        team4.setTeamName("Eco Warriors");
        teamRepository.save(team4);

        Team team5 = new Team();
        team5.setTeamName("Caring Hearts");
        teamRepository.save(team5);

        Team team6 = new Team();
        team6.setTeamName("Knowledge Spreaders");
        teamRepository.save(team6);

        // Link Teams to Activities (TeamActivity)
        TeamActivity ta1 = new TeamActivity();
        ta1.setTeam(team1);
        ta1.setActivity(activity1);
        teamActivityRepository.save(ta1);

        TeamActivity ta2 = new TeamActivity();
        ta2.setTeam(team2);
        ta2.setActivity(activity2);
        teamActivityRepository.save(ta2);

        TeamActivity ta3 = new TeamActivity();
        ta3.setTeam(team3);
        ta3.setActivity(activity3);
        teamActivityRepository.save(ta3);

        TeamActivity ta4 = new TeamActivity();
        ta4.setTeam(team4);
        ta4.setActivity(activity4);
        teamActivityRepository.save(ta4);

        TeamActivity ta5 = new TeamActivity();
        ta5.setTeam(team5);
        ta5.setActivity(activity5);
        teamActivityRepository.save(ta5);

        TeamActivity ta6 = new TeamActivity();
        ta6.setTeam(team6);
        ta6.setActivity(activity6);
        teamActivityRepository.save(ta6);

        // Link Teams to Past Activities
        TeamActivity taPast1 = new TeamActivity();
        taPast1.setTeam(team1);
        taPast1.setActivity(activityPast1);
        teamActivityRepository.save(taPast1);

        TeamActivity taPast2 = new TeamActivity();
        taPast2.setTeam(team2);
        taPast2.setActivity(activityPast2);
        teamActivityRepository.save(taPast2);

        TeamActivity taPast3 = new TeamActivity();
        taPast3.setTeam(team3);
        taPast3.setActivity(activityPast3);
        teamActivityRepository.save(taPast3);


        log.info("-------------------------------");
        log.info("Database initialized with sample data:");
        log.info("Activities: " + activityRepository.count());
        log.info("Volunteers: " + volunteerRepository.count());
        log.info("Activity-Volunteer links: " + activityVolunteerRepository.count());
        log.info("Teams: " + teamRepository.count());
        log.info("Team-Activity links: " + teamActivityRepository.count());
        log.info("-------------------------------");
    }
}