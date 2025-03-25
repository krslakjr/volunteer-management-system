package com.example.activitymanagement.dto;

public class ActivityDTO {

    private Long activityId;
    private String description;
    private String date;
    private String location;
    private int volunteersNeeded;

    public ActivityDTO() {}

    public ActivityDTO(Long activityId, String description, String date, String location, int volunteersNeeded) {
        this.activityId = activityId;
        this.description = description;
        this.date = date;
        this.location = location;
        this.volunteersNeeded = volunteersNeeded;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getVolunteersNeeded() {
        return volunteersNeeded;
    }

    public void setVolunteersNeeded(int volunteersNeeded) {
        this.volunteersNeeded = volunteersNeeded;
    }
}
