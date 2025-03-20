package com.example.userservice.service;

import com.example.userservice.models.SocialShare;
import com.example.userservice.repository.SocialShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialShareService {

    @Autowired
    private SocialShareRepository socialShareRepository;

    // Get all SocialShares
    public List<SocialShare> getAllSocialShares() {
        return socialShareRepository.findAll();
    }

    // Get SocialShare by ID
    public Optional<SocialShare> getSocialShareById(Long id) {
        return socialShareRepository.findById(id);
    }

    // Create a new SocialShare
    public SocialShare createSocialShare(SocialShare socialShare) {
        return socialShareRepository.save(socialShare);
    }

    // Update an existing SocialShare
    public Optional<SocialShare> updateSocialShare(Long id, SocialShare socialShare) {
        if (socialShareRepository.existsById(id)) {
            return Optional.of(socialShareRepository.save(socialShare));
        }
        return Optional.empty();
    }

    // Delete a SocialShare
    public void deleteSocialShare(Long id) {
        socialShareRepository.deleteById(id);
    }
}
