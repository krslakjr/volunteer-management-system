package com.example.participationservice;

import com.example.participationservice.models.*;
import com.example.participationservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private ActivityService activityService;

    @Override
    public void run(String... args) throws Exception {
        Activity activity1 = new Activity();
        activity1.setDescription("Community Cleanup Event");
        activity1.setDate(new Date());
        activity1.setLocation("City Park");
        activity1.setVolunteersNeeded(20);
        activityService.saveActivity(activity1);

        Activity activity2 = new Activity();
        activity2.setDescription("Charity Fundraising");
        activity2.setDate(new Date());
        activity2.setLocation("Community Hall");
        activity2.setVolunteersNeeded(30);
        activityService.saveActivity(activity2);

        System.out.println("Activities saved.");

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setName("John Doe");
        volunteer1.setContactInfo("john.doe@example.com");
        volunteerService.saveVolunteer(volunteer1);

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setName("Jane Smith");
        volunteer2.setContactInfo("jane.smith@example.com");
        volunteerService.saveVolunteer(volunteer2);

        System.out.println("Volunteers saved.");

        Participation participation1 = new Participation();
        participation1.setVolunteer(volunteer1);
        participation1.setActivity(activity1);
        participation1.setRegistrationDate(new Date());
        participation1.setAttendanceStatus("Confirmed");
        participationService.saveParticipation(participation1);

        Participation participation2 = new Participation();
        participation2.setVolunteer(volunteer2);
        participation2.setActivity(activity2);
        participation2.setRegistrationDate(new Date());
        participation2.setAttendanceStatus("Confirmed");
        participationService.saveParticipation(participation2);

        System.out.println("Participations saved.");

        Recommendation recommendation1 = new Recommendation();
        recommendation1.setVolunteer(volunteer1);
        recommendation1.setRecommendationActivity(activity1);
        recommendation1.setDateGenerated(new Date());
        recommendationService.saveRecommendation(recommendation1);

        Recommendation recommendation2 = new Recommendation();
        recommendation2.setVolunteer(volunteer2);
        recommendation2.setRecommendationActivity(activity2);
        recommendation2.setDateGenerated(new Date());
        recommendationService.saveRecommendation(recommendation2);

        System.out.println("Recommendations saved.");

        Certificate certificate1 = new Certificate();
        certificate1.setVolunteer(volunteer1);
        certificate1.setActivity(activity1);
        certificate1.setIssueDate(new Date());
        certificate1.setCertificateStatus("Issued");
        certificateService.saveCertificate(certificate1);

        Certificate certificate2 = new Certificate();
        certificate2.setVolunteer(volunteer2);
        certificate2.setActivity(activity2);
        certificate2.setIssueDate(new Date());
        certificate2.setCertificateStatus("Issued");
        certificateService.saveCertificate(certificate2);

        System.out.println("Certificates saved.");
    }
}
