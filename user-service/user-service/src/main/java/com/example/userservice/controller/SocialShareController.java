package com.example.userservice.controller;

import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.models.Permission;
import com.example.userservice.models.SocialShare;
import com.example.userservice.service.SocialShareService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/social-shares")
public class SocialShareController {

    @Autowired
    private SocialShareService socialShareService;

    @GetMapping
    public List<SocialShare> getAllSocialShares() {
        return socialShareService.getAllSocialShares();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialShare> getSocialShareById(@PathVariable Long id) {
        Optional<SocialShare> socialShare = socialShareService.getSocialShareById(id);
        return socialShare.map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Social share not found with id " + id, "id"));
}

    @PostMapping
    public ResponseEntity<SocialShare> createSocialShare(@Valid @RequestBody SocialShare socialShare) {
        try {
            SocialShare createdSocialShare = socialShareService.createSocialShare(socialShare);
            return new ResponseEntity<>(createdSocialShare, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialShare> updateSocialShare(@PathVariable Long id, @Valid @RequestBody SocialShare socialShare) {
        Optional<SocialShare> updatedSocialShare = socialShareService.updateSocialShare(id, socialShare);
        return updatedSocialShare.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSocialShare(@PathVariable Long id) {
        try {
            socialShareService.deleteSocialShare(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}