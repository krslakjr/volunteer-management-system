package com.example.activitymanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    private String description;
    private String date;
    private String location;
    private int volunteersNeeded;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActivityVolunteer> activityVolunteers;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // Sprečava ciklično ugnježđivanje
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
