package com.example.notificationservice.event;

import com.example.notificationservice.config.RabbitMQConfig;
import com.example.notificationservice.models.Activity;
import com.example.notificationservice.models.Notification;
import com.example.notificationservice.models.Organizer;
import com.example.notificationservice.models.Volunteer;
import com.example.notificationservice.repository.ActivityRepository;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.repository.OrganizerRepository;
import com.example.notificationservice.repository.VolunteerRepository;
import com.example.notificationservice.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Component
public class EventListener {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private EmailService emailService;

    private final RestTemplate restTemplate;

    public EventListener(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void handleParticipationCreatedEvent(ParticipationCreatedEvent event) {
        Volunteer volunteer = volunteerRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));
        Activity activity = activityRepository.findById(event.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        Organizer organizer = organizerRepository.findById(activity.getOrganizer().getOrganizerId())
                .orElseThrow(() -> new RuntimeException("Organizer not found"));
        Notification notification = new Notification();
        notification.setVolunteer(volunteer);
        notification.setActivity(activity);
        notification.setOrganizer(organizer);
        notification.setMessage("You have successfully signed up for the activity: " + activity.getTitle());
        notification.setType("Participation confirmed");
        notification.setTimestamp(new Date());
        notification.setRead(false);
        notificationRepository.save(notification);
        String email = volunteer.getEmail();
        if (email != null && !email.isBlank()) {
            emailService.sendNotificationEmail(
                    email,
                    "Potvrda o volontiranju",
                    "Zahvaljujemo se što ste se prijavili za aktivnost: " + activity.getTitle()
            );
        }
    }

    @RabbitListener(queues = RabbitMQConfig.FEEDBACK_QUEUE)
    public void handleFeedbackCreatedEvent(FeedbackCreatedEvent event) {
        Volunteer volunteer = volunteerRepository.findById(event.getVolunteerId())
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));
        Activity activity = activityRepository.findById(event.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        Organizer organizer = organizerRepository.findById(activity.getOrganizer().getOrganizerId())
                .orElseThrow(() -> new RuntimeException("Organizer not found"));
        Notification notification = new Notification();
        notification.setVolunteer(volunteer);
        notification.setActivity(activity);
        notification.setOrganizer(organizer);
        notification.setMessage("Feedback left for the activity: " + activity.getTitle());
        notification.setType("Feedback confirmed");
        notification.setTimestamp(new Date());
        notification.setRead(false);
        notificationRepository.save(notification);
        String token = event.getJwtToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<String>> response = restTemplate.exchange(
                "http://user-service/api/users/admins/emails", // zamijeni ako koristiš service discovery
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        List<String> adminEmails = response.getBody();
        String emailBody = String.format("""
        Novi feedback je kreiran!
        
        Aktivnost: %s
        Volonter: %s
        Poruka: %s
        
        Pošiljalac: %s
        """,
                activity.getTitle(),
                volunteer.getName(),
                event.getComment(),
                volunteer.getEmail()
        );

        for (String email : adminEmails) {
            emailService.sendNotificationEmail(
                    email,
                    "Novi feedback kreiran",
                    emailBody);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        String subject = "Dobrodošli na našu platformu za volontiranje!";
        String body = String.format("""
                Poštovani %s %s,

                Hvala vam što ste se registrovali na našu platformu.

                Korisničko ime: %s
                Email adresa: %s

                Uloge: %s

                Radujemo se vašem doprinosu!

                Srdačan pozdrav,
                Vaš tim.
                """,
                event.getFirstName(),
                event.getLastName(),
                event.getUsername(),
                event.getEmail(),
                String.join(", ", event.getRoleNames())
        );

        emailService.sendNotificationEmail(event.getEmail(), subject, body);
    }
}