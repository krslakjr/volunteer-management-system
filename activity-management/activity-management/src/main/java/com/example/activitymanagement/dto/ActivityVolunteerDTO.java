package com.example.activitymanagement.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class ActivityVolunteerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD

    @NotNull(message = "Activity ID is required")
    private Long activityId;

=======
    @NotNull(message = "Activity ID is required")
    private Long activityId;
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
    @NotNull(message = "Volunteer ID is required")
    private Long volunteerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }
}
