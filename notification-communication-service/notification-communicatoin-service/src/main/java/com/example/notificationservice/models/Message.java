package com.example.notificationservice.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Volunteer sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Volunteer receiver;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    private String content;
    private Date timestamp;

    // Getters and Setters
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Volunteer getSender() {
        return sender;
    }

    public void setSender(Volunteer sender) {
        this.sender = sender;
    }

    public Volunteer getReceiver() {
        return receiver;
    }

    public void setReceiver(Volunteer receiver) {
        this.receiver = receiver;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
