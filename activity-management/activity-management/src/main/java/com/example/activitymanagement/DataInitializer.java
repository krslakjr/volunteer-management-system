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
        // Kreiranje aktivnosti
        Activity activity1 = new Activity();
        activity1.setDescription("Sadnja drveća u parku");
        activity1.setDate("2025-04-10");
        activity1.setLocation("Gradski park");
        activity1.setVolunteersNeeded(10);
        activityRepository.save(activity1);

        // Kreiranje volontera
        Volunteer volunteer1 = new Volunteer();
        volunteer1.setName("Ana");
        volunteer1.setContactInfo("ana@example.com");
        volunteerRepository.save(volunteer1);

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setName("Marko");
        volunteer2.setContactInfo("marko@example.com");
        volunteerRepository.save(volunteer2);

        // Povezivanje volontera s aktivnošću
        ActivityVolunteer av1 = new ActivityVolunteer();
        av1.setActivity(activity1);
        av1.setVolunteer(volunteer1);
        activityVolunteerRepository.save(av1);

        ActivityVolunteer av2 = new ActivityVolunteer();
        av2.setActivity(activity1);
        av2.setVolunteer(volunteer2);
        activityVolunteerRepository.save(av2);

        // Kreiranje tima
        Team team1 = new Team();
        team1.setTeamName("Zeleni Tim");
        teamRepository.save(team1);

        // Povezivanje tima s aktivnošću
        TeamActivity ta1 = new TeamActivity();
        ta1.setTeam(team1);
        ta1.setActivity(activity1);
        teamActivityRepository.save(ta1);

        log.info("-------------------------------");
        log.info("Uneseni podaci u bazu:");
        log.info("Aktivnost: " + activity1.getDescription() + " - " + activity1.getLocation());
        log.info("Volonter 1: " + volunteer1.getName() + " - " + volunteer1.getContactInfo());
        log.info("Volonter 2: " + volunteer2.getName() + " - " + volunteer2.getContactInfo());
        log.info("Tim: " + team1.getTeamName());
        log.info("TimActivity veza unesena: " + team1.getTeamName() + " -> " + activity1.getDescription());
        log.info("Povezivanje volontera i aktivnosti uneseno u activity_volunteer tabelu.");
        log.info("-------------------------------");
    }
}
