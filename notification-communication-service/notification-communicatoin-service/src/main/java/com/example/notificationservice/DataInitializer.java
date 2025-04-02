package com.example.notificationservice;

import com.example.notificationservice.models.*;
import com.example.notificationservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private EngagementStatisticsService engagementStatisticsService;

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private ForumPostService forumPostService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MessageService messageService;

    @Override
    public void run(String... args) throws Exception {
        Organizer organizer1 = new Organizer();
        organizer1.setName("John's Volunteer Group");
        organizer1.setEmail("johnsgroup@example.com");
        organizer1.setPhoneNumber("1234567890");
        organizerService.saveOrganizer(organizer1);

        Organizer organizer2 = new Organizer();
        organizer2.setName("Jane's Fundraisers");
        organizer2.setEmail("janesfundraisers@example.com");
        organizer2.setPhoneNumber("9876543210");
        organizerService.saveOrganizer(organizer2);

        System.out.println("Organizers saved.");

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setName("John Doe");
        volunteer1.setEmail("john.doe@example.com");
        volunteer1.setPhoneNumber("5551234");
        volunteerService.saveVolunteer(volunteer1);

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setName("Jane Smith");
        volunteer2.setEmail("jane.smith@example.com");
        volunteer2.setPhoneNumber("5555678");
        volunteerService.saveVolunteer(volunteer2);

        System.out.println("Volunteers saved.");

        EngagementStatistics engagementStatistics1 = new EngagementStatistics();
        engagementStatistics1.setVolunteer(volunteer1);
        engagementStatistics1.setTotalActivities(10);
        engagementStatistics1.setMessagesSent(50);
        engagementStatistics1.setForumPostsMade(5);
        engagementStatistics1.setNotificationsReceived(20);
        engagementStatisticsService.saveEngagementStatistics(engagementStatistics1);

        EngagementStatistics engagementStatistics2 = new EngagementStatistics();
        engagementStatistics2.setVolunteer(volunteer2);
        engagementStatistics2.setTotalActivities(15);
        engagementStatistics2.setMessagesSent(80);
        engagementStatistics2.setForumPostsMade(8);
        engagementStatistics2.setNotificationsReceived(30);
        engagementStatisticsService.saveEngagementStatistics(engagementStatistics2);

        System.out.println("Engagement statistics saved.");

        Activity activity1 = new Activity();
        activity1.setTitle("Community Cleanup");
        activity1.setDescription("A cleanup event in the local park.");
        activity1.setDate(new Date());
        activity1.setLocation("City Park");
        activity1.setOrganizer(organizer1); 
        activityService.saveActivity(activity1);

        Activity activity2 = new Activity();
        activity2.setTitle("Fundraising Event");
        activity2.setDescription("Fundraiser for local animal shelter.");
        activity2.setDate(new Date());
        activity2.setLocation("Community Hall");
        activity2.setOrganizer(organizer2); 
        activityService.saveActivity(activity2);

        System.out.println("Activities saved.");

        Notification notification1 = new Notification();
        notification1.setVolunteer(volunteer1);
        notification1.setActivity(activity1);
        notification1.setOrganizer(organizer1);
        notification1.setMessage("Activity reminder: Community Cleanup tomorrow at 9 AM.");
        notification1.setType("Reminder");
        notification1.setTimestamp(new Date());
        notification1.setRead(false);
        notificationService.saveNotification(notification1);

        Notification notification2 = new Notification();
        notification2.setVolunteer(volunteer2);
        notification2.setActivity(activity2);
        notification2.setOrganizer(organizer2);
        notification2.setMessage("New forum post: Fundraising event announced!");
        notification2.setType("Announcement");
        notification2.setTimestamp(new Date());
        notification2.setRead(false);
        notificationService.saveNotification(notification2);

        System.out.println("Notifications saved.");

        ForumPost forumPost1 = new ForumPost();
        forumPost1.setActivity(activity1);
        forumPost1.setAuthor(volunteer1);
        forumPost1.setOrganizer(organizer1);
        forumPost1.setTimestamp(new Date());
        forumPost1.setContent("How many people are going to be there?");
        forumPostService.saveForumPost(forumPost1);

        ForumPost forumPost2 = new ForumPost();
        forumPost2.setActivity(activity2);
        forumPost2.setAuthor(volunteer2);
        forumPost2.setOrganizer(organizer2);
        forumPost2.setContent("When does the cleanup start?");
        forumPost2.setTimestamp(new Date());
        forumPostService.saveForumPost(forumPost2);

        System.out.println("Forum posts saved.");

        Message message1 = new Message();
        message1.setSender(volunteer1);
        message1.setReceiver(volunteer2);
        message1.setOrganizer(organizer1);
        message1.setContent("Hey Jane, are you joining the Community Cleanup tomorrow?");
        message1.setTimestamp(new Date());
        messageService.saveMessage(message1);

        Message message2 = new Message();
        message2.setSender(volunteer2);
        message2.setReceiver(volunteer1);
        message2.setOrganizer(organizer2);
        message2.setContent("Yes, I'll be there at 9 AM!");
        message2.setTimestamp(new Date());
        messageService.saveMessage(message2);

        System.out.println("Messages saved.");
    }
}