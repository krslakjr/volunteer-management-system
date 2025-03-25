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

    public List<SocialShare> getAllSocialShares() {
        return socialShareRepository.findAll();
    }

    public Optional<SocialShare> getSocialShareById(Long id) {
        return socialShareRepository.findById(id);
    }

    public SocialShare createSocialShare(SocialShare socialShare) {
        return socialShareRepository.save(socialShare);
    }

    public Optional<SocialShare> updateSocialShare(Long id, SocialShare socialShare) {
        if (socialShareRepository.existsById(id)) {
            return Optional.of(socialShareRepository.save(socialShare));
        }
        return Optional.empty();
    }

    public void deleteSocialShare(Long id) {
        socialShareRepository.deleteById(id);
    }
}
