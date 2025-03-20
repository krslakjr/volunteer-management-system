package com.example.activitymanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "volunteer")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long volunteerId;

    private String name;

    private String contactInfo;

    // Veza sa ActivityVolunteer (M:N sa Activity)
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Dodajemo ovu anotaciju da bismo sprečili beskonačno ugnježđivanje
    private List<ActivityVolunteer> activityVolunteers;

    // Getteri i Setteri
    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<ActivityVolunteer> getActivityVolunteers() {
        return activityVolunteers;
    }

    public void setActivityVolunteers(List<ActivityVolunteer> activityVolunteers) {
        this.activityVolunteers = activityVolunteers;
    }
}
