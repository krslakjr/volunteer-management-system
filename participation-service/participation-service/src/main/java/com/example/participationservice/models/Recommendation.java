package com.example.participationservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "recommendation_activity_id")
    @JsonBackReference
    private Activity recommendationActivity;

    private Date dateGenerated;

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
