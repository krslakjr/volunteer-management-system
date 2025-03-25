package com.example.notificationservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "forum_post")
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference  
    private Volunteer author;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    @JsonManagedReference  
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonBackReference  
    private Organizer organizer;

    private String content;
    private Date timestamp;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Volunteer getAuthor() {
        return author;
    }

    public void setAuthor(Volunteer author) {
        this.author = author;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
