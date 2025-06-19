package com.example.participationservice.event;

import java.time.Instant;

public class ParticipationCreatedEvent {
    private Long userId;
    private String email;
    private Long activityId;
    private Instant timestamp;

    public ParticipationCreatedEvent() {}

    public ParticipationCreatedEvent(Long userId, String email, Long activityId, Instant timestamp) {
        this.userId = userId;
        this.email = email;
        this.activityId = activityId;
        this.timestamp = timestamp;
    }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Long getActivityId() { return activityId; }

    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public Instant getTimestamp() { return timestamp; }

    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
