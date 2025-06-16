package com.example.notificationservice.event;

public class FeedbackCreatedEvent {
    private Long volunteerId;
    private Long activityId;
    private String comment;
    private int rating;
    private String jwtToken;

    public FeedbackCreatedEvent() {}

    public FeedbackCreatedEvent(Long volunteerId, Long activityId, String comment, int rating, String jwtToken) {
        this.volunteerId = volunteerId;
        this.activityId = activityId;
        this.comment = comment;
        this.rating = rating;
        this.jwtToken = jwtToken;
    }

    public Long getVolunteerId() { return volunteerId; }

    public int getRating() { return rating; }

    public Long getActivityId() { return activityId; }

    public String getComment() { return comment; }

    public String getJwtToken() { return jwtToken; }

    public void setVolunteerId(Long volunteerId) {  this.volunteerId = volunteerId; }

    public void setActivityId(Long activityId) {  this.activityId = activityId; }

    public void setComment(String comment) { this.comment = comment; }

    public void setRating(int rating) { this.rating = rating; }

    public void setJwtToken(String jwtToken) { this.jwtToken = jwtToken; }
}

