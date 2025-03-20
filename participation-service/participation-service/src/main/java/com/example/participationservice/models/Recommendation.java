package com.example.participationservice.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "recommendation")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "recommendation_activity_id")
    private Activity recommendationActivity;

    private Date dateGenerated;

    // Getters and Setters
    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Activity getRecommendationActivity() {
        return recommendationActivity;
    }

    public void setRecommendationActivity(Activity recommendationActivity) {
        this.recommendationActivity = recommendationActivity;
    }

    public Date getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(Date dateGenerated) {
        this.dateGenerated = dateGenerated;
    }
}
