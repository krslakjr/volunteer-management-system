package com.example.userservice.models;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_permission", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "permission_id"})
})
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Veza sa User modelom sa @JsonBackReference da se spreči beskonačna petlja prilikom serijalizacije
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // Veza sa Permission modelom sa @JsonBackReference da se spreči beskonačna petlja prilikom serijalizacije
    @ManyToOne
    @JoinColumn(name = "permission_id")
    @JsonBackReference
    private Permission permission;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for permission
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
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
