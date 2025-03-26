package com.example.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "engagement_statistics")
public class EngagementStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    @JsonBackReference ("volunteerEngagementReference")
    private Volunteer volunteer;

    private int totalActivities;
    private int messagesSent;
    private int forumPostsMade;
    private int notificationsReceived;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public int getTotalActivities() {
        return totalActivities;
    }

    public void setTotalActivities(int totalActivities) {
        this.totalActivities = totalActivities;
    }

    public int getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(int messagesSent) {
        this.messagesSent = messagesSent;
    }

    public int getForumPostsMade() {
        return forumPostsMade;
    }

    public void setForumPostsMade(int forumPostsMade) {
        this.forumPostsMade = forumPostsMade;
    }

    public int getNotificationsReceived() {
        return notificationsReceived;
    }

    public void setNotificationsReceived(int notificationsReceived) {
        this.notificationsReceived = notificationsReceived;
    }
}
