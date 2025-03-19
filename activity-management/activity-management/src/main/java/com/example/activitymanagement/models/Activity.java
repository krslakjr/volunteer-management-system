package com.example.activitymanagement.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    private String description;
    
    @Temporal(TemporalType.DATE)
    private Date date;

    private String location;
    
    private int volunteersNeeded;

    // Veza sa ActivityVolunteer (M:N sa Volunteer)
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActivityVolunteer> activityVolunteers;

    // Veza sa TeamActivity (M:N sa Team)
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamActivity> teamActivities;

    // Getteri i Setteri
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public List<ActivityVolunteer> getActivityVolunteers() {
        return activityVolunteers;
    }

    public void setActivityVolunteers(List<ActivityVolunteer> activityVolunteers) {
        this.activityVolunteers = activityVolunteers;
    }

    public List<TeamActivity> getTeamActivities() {
        return teamActivities;
    }

    public void setTeamActivities(List<TeamActivity> teamActivities) {
        this.teamActivities = teamActivities;
    }
}
