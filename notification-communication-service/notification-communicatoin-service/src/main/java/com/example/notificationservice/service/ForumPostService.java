package com.example.notificationservice.service;

import com.example.notificationservice.models.ForumPost;
import com.example.notificationservice.repository.ForumPostRepository;
import org.springframework.stereotype.Service;
import com.example.notificationservice.exception.ResourceNotFoundException;


import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ForumPostService {

    private final ForumPostRepository forumPostRepository;

    public ForumPostService(ForumPostRepository forumPostRepository) {
        this.forumPostRepository = forumPostRepository;
    }
    public void saveForumPost(ForumPost forumpost) {
        forumPostRepository.save(forumpost);
    }    

    public List<ForumPost> getAllForumPosts() {
        return forumPostRepository.findAll();
    }

    
    public List<ForumPost> getAllForumPosts(Pageable pageable) {
        Page<ForumPost> page = forumPostRepository.findAll(pageable);
        return page.getContent();
    }

    public Optional<ForumPost> getForumPostById(Long id) {
        return forumPostRepository.findById(id);
    }

    @Transactional
    public ForumPost createForumPost(ForumPost forumPost) {
        return forumPostRepository.save(forumPost);
    }

    @Transactional
    public ForumPost updateForumPost(Long id, ForumPost updatedForumPost) {
        return forumPostRepository.findById(id)
                .map(forumPost -> {
                    forumPost.setContent(updatedForumPost.getContent());
                    forumPost.setTimestamp(updatedForumPost.getTimestamp());
                    forumPost.setActivity(updatedForumPost.getActivity());
                    forumPost.setAuthor(updatedForumPost.getAuthor());
                    forumPost.setOrganizer(updatedForumPost.getOrganizer());
                    return forumPostRepository.save(forumPost);
                })
                .orElseThrow(() -> new ResourceNotFoundException("ForumPost not found with id " + id, "id"));
    }
    
    @Transactional
    public void deleteForumPost(Long id) {
        forumPostRepository.deleteById(id);
    }
}