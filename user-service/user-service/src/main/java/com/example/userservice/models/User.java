package com.example.userservice.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String profilePicture;

    // Role veza sa @JsonManagedReference, omogućava serijalizaciju role u User modelu
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonManagedReference
    private Role role;

    // UserPermissions veza sa @JsonManagedReference, omogućava serijalizaciju u User modelu
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<UserPermission> userPermissions;

    // SocialShares veza sa @JsonManagedReference, omogućava serijalizaciju u User modelu
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SocialShare> socialShares;

    // VolunteerCertificates veza sa @JsonManagedReference, omogućava serijalizaciju u User modelu
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<VolunteerCertificate> volunteerCertificates;

    @ManyToMany
    @JoinTable(
        name = "user_activity",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    @JsonIgnore // Ignoriše učestvovanje u aktivnosti prilikom serijalizacije
    private List<Activity> activities;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for passwordHash
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Getter and Setter for profilePicture
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Getter and Setter for role
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Getter and Setter for userPermissions
    public List<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(List<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    // Getter and Setter for socialShares
    public List<SocialShare> getSocialShares() {
        return socialShares;
    }

    public void setSocialShares(List<SocialShare> socialShares) {
        this.socialShares = socialShares;
    }

    // Getter and Setter for volunteerCertificates
    public List<VolunteerCertificate> getVolunteerCertificates() {
        return volunteerCertificates;
    }

    public void setVolunteerCertificates(List<VolunteerCertificate> volunteerCertificates) {
        this.volunteerCertificates = volunteerCertificates;
    }

    // Getter and Setter for activities
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    // Getter and Setter for createdAt
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Getter and Setter for updatedAt
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Automatically set timestamps before persisting
    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    // Automatically update timestamp before updating
    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }
}
