package com.example.userservice.service;

import com.example.userservice.models.SocialShare;
import com.example.userservice.repository.SocialShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class SocialShareService {

    @Autowired
    private SocialShareRepository socialShareRepository;

    public List<SocialShare> getAllSocialShares() {
        return socialShareRepository.findAll();
    }

    public List<SocialShare> getAllSocialShares(Pageable pageable) {
        Page<SocialShare> page = socialShareRepository.findAll(pageable);
        return page.getContent();
    }

    public Optional<SocialShare> getSocialShareById(Long id) {
        return socialShareRepository.findById(id);
    }

    @Transactional
    public SocialShare createSocialShare(SocialShare socialShare) {
        return socialShareRepository.save(socialShare);
    }

    @Transactional
    public Optional<SocialShare> updateSocialShare(Long id, SocialShare socialShare) {
        if (socialShareRepository.existsById(id)) {
            return Optional.of(socialShareRepository.save(socialShare));
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteSocialShare(Long id) {
        socialShareRepository.deleteById(id);
    }
}
