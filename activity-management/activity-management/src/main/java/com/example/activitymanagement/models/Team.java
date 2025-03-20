package com.example.activitymanagement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    private String teamName;

    // Veza sa TeamActivity (M:N sa Activity)
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference  // Sprečava ciklično ugnježđivanje
    private List<TeamActivity> teamActivities;

    // Getteri i Setteri
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<TeamActivity> getTeamActivities() {
        return teamActivities;
    }

    public void setTeamActivities(List<TeamActivity> teamActivities) {
        this.teamActivities = teamActivities;
    }
}
