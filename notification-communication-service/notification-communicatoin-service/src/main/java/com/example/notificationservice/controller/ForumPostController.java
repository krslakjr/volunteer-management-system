package com.example.notificationservice.controller;

import com.example.notificationservice.models.ForumPost;
import com.example.notificationservice.service.ForumPostService;
import jakarta.validation.Valid;
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
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ForumPost createForumPost(@Valid @RequestBody ForumPost forumPost) {
        return forumPostService.createForumPost(forumPost);
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<ForumPost> updateForumPost(@PathVariable Long id, @Valid @RequestBody ForumPost updatedForumPost) {
=======
    public ResponseEntity<ForumPost> updateForumPost(@PathVariable Long id,@Valid @RequestBody ForumPost updatedForumPost) {
>>>>>>> 1f92f07d26c618f4ab802b3c248b0b97d353dacb
        try {
            ForumPost forumPost = forumPostService.updateForumPost(id, updatedForumPost);
            return ResponseEntity.ok(forumPost);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForumPost(@PathVariable Long id) {
        forumPostService.deleteForumPost(id);
        return ResponseEntity.noContent().build();
    }
}
