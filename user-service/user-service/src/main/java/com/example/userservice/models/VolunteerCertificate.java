package com.example.userservice.models;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "volunteer_certificate", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "activity_id"})
})
public class VolunteerCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    // Veza sa User modelom sa @JsonBackReference da se spreči beskonačna petlja prilikom serijalizacije
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // Veza sa Activity modelom sa @JsonBackReference da se spreči beskonačna petlja prilikom serijalizacije
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    @JsonBackReference
    private Activity activity;

    private Date certificateDate;
    private String certificatePdfLink;
    private Date issuedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Getter and Setter for certificateId
    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    // Getter and Setter for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for activity
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    // Getter and Setter for certificateDate
    public Date getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(Date certificateDate) {
        this.certificateDate = certificateDate;
    }

    // Getter and Setter for certificatePdfLink
    public String getCertificatePdfLink() {
        return certificatePdfLink;
    }

    public void setCertificatePdfLink(String certificatePdfLink) {
        this.certificatePdfLink = certificatePdfLink;
    }

    // Getter and Setter for issuedAt
    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
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
