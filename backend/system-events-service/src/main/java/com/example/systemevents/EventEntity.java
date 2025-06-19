package com.example.systemevents;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "Event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant timestamp;
    private String microservice;
    private String userId;
    private String actionType;
    private String resourceName;
    private String outcome;
    private String errorMessage;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Instant getTimestamp() { return timestamp; }

    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getMicroservice() { return microservice; }

    public void setMicroservice(String microservice) { this.microservice = microservice; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getActionType() { return actionType; }

    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getResourceName() { return resourceName; }

    public void setResourceName(String resourceName) { this.resourceName = resourceName; }

    public String getOutcome() { return outcome; }

    public void setOutcome(String outcome) { this.outcome = outcome; }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
