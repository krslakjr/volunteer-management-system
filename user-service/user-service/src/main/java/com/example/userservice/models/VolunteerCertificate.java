package com.example.userservice.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "volunteer_certificate")
public class VolunteerCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private Date certificateDate;
    private String certificatePdfLink;
    private Date issuedAt;

    // Getteri i setteri
    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Date getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(Date certificateDate) {
        this.certificateDate = certificateDate;
    }

    public String getCertificatePdfLink() {
        return certificatePdfLink;
    }

    public void setCertificatePdfLink(String certificatePdfLink) {
        this.certificatePdfLink = certificatePdfLink;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }
}
