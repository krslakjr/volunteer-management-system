package com.example.notificationservice.controller;

import com.example.notificationservice.exception.ResourceNotFoundException;
import com.example.notificationservice.models.ForumPost;
import com.example.notificationservice.service.ForumPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forum-posts")
public class ForumPostController {

    private final ForumPostService forumPostService;

    public ForumPostController(ForumPostService forumPostService) {
        this.forumPostService = forumPostService;
    }

    @GetMapping
    public List<ForumPost> getAllForumPosts() {
        return forumPostService.getAllForumPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumPost> getForumPostById(@PathVariable Long id) {
        Optional<ForumPost> forumPost = forumPostService.getForumPostById(id);
        return forumPost.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("ForumPost not found with id " + id, "id"));
    }

    @PostMapping
    public ResponseEntity<ForumPost> createForumPost(@Valid @RequestBody ForumPost forumPost) {
        try {
            ForumPost createdForumPost = forumPostService.createForumPost(forumPost);
            return new ResponseEntity<>(createdForumPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumPost> updateForumPost(@PathVariable Long id, @Valid @RequestBody ForumPost updatedForumPost) {
        try {
            ForumPost forumPost = forumPostService.updateForumPost(id, updatedForumPost);
            return ResponseEntity.ok(forumPost);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumPost(@PathVariable Long id) {
        try {
            forumPostService.deleteForumPost(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
