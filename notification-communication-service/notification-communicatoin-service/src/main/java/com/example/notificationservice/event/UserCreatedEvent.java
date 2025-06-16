package com.example.notificationservice.event;

import java.util.Date;
import java.util.Set;

public class UserCreatedEvent {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
    private Date createdAt;
    private Set<String> roleNames;

    public UserCreatedEvent() {
    }

    public UserCreatedEvent(String username, String firstName, String lastName, String email,
                            String profilePicture, Date createdAt, Set<String> roleNames) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePicture = profilePicture;
        this.createdAt = createdAt;
        this.roleNames = roleNames;
    }

    // Getteri i setteri

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }
}
