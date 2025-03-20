package com.example.userservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference; // Dodato
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    private String activityName;
    private Date activityDate;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id")
    @JsonManagedReference // Serijalizuje organizatora
    private User organizer;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SocialShare> socialShares;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<VolunteerCertificate> volunteerCertificates;

    @ManyToMany(mappedBy = "activities")
    @JsonBackReference // Ignoriše učesnike prilikom serijalizacije
    private List<User> participants;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getter i Setter metod za activityId
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    // Getter i Setter metod za activityName
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    // Getter i Setter metod za activityDate
    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    // Getter i Setter metod za description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter i Setter metod za organizer
    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    // Getter i Setter metod za socialShares
    public List<SocialShare> getSocialShares() {
        return socialShares;
    }

    public void setSocialShares(List<SocialShare> socialShares) {
        this.socialShares = socialShares;
    }

    // Getter i Setter metod za volunteerCertificates
    public List<VolunteerCertificate> getVolunteerCertificates() {
        return volunteerCertificates;
    }

    public void setVolunteerCertificates(List<VolunteerCertificate> volunteerCertificates) {
        this.volunteerCertificates = volunteerCertificates;
    }

    // Getter i Setter metod za participants
    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    // Getter i Setter metod za createdAt
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Getter i Setter metod za updatedAt
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Automatski setovanje timestamps pre nego što se entitet sačuva
    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    // Automatski ažuriranje timestamp pre nego što se entitet ažurira
    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }
}
