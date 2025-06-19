package com.example.notificationservice;

import com.example.notificationservice.service.ForumPostService;
import com.example.notificationservice.models.ForumPost;
import com.example.notificationservice.repository.ForumPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumPostServiceTest {

    @Mock
    private ForumPostRepository forumPostRepository;

    @InjectMocks
    private ForumPostService forumPostService;

    private ForumPost forumPost;

    @BeforeEach
    void setUp() {
        forumPost = new ForumPost();
        forumPost.setPostId(1L);
        forumPost.setContent("Test content");
    }

    @Test
    void testSaveForumPost() {
        forumPostService.saveForumPost(forumPost);
        verify(forumPostRepository, times(1)).save(forumPost);
    }

    @Test
    void testGetAllForumPosts() {
        when(forumPostRepository.findAll()).thenReturn(Arrays.asList(forumPost));
        List<ForumPost> posts = forumPostService.getAllForumPosts();
        assertEquals(1, posts.size());
        assertEquals("Test content", posts.get(0).getContent());
    }

    @Test
    void testGetForumPostById_Found() {
        when(forumPostRepository.findById(1L)).thenReturn(Optional.of(forumPost));
        Optional<ForumPost> retrievedPost = forumPostService.getForumPostById(1L);
        assertTrue(retrievedPost.isPresent());
        assertEquals("Test content", retrievedPost.get().getContent());
    }

    @Test
    void testGetForumPostById_NotFound() {
        when(forumPostRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<ForumPost> retrievedPost = forumPostService.getForumPostById(2L);
        assertFalse(retrievedPost.isPresent());
    }

    @Test
    void testCreateForumPost() {
        when(forumPostRepository.save(forumPost)).thenReturn(forumPost);
        ForumPost createdPost = forumPostService.createForumPost(forumPost);
        assertNotNull(createdPost);
        assertEquals("Test content", createdPost.getContent());
    }

    @Test
    void testUpdateForumPost_Success() {
        ForumPost updatedForumPost = new ForumPost();
        updatedForumPost.setContent("Updated content");
        
        when(forumPostRepository.findById(1L)).thenReturn(Optional.of(forumPost));
        when(forumPostRepository.save(any(ForumPost.class))).thenReturn(updatedForumPost);

        ForumPost result = forumPostService.updateForumPost(1L, updatedForumPost);
        assertEquals("Updated content", result.getContent());
    }

    @Test
void testUpdateForumPost_NotFound() {
    ForumPost updatedForumPost = new ForumPost();
    when(forumPostRepository.findById(2L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> 
        forumPostService.updateForumPost(2L, updatedForumPost)
    );
    assertEquals("ForumPost not found with id 2", exception.getMessage());
}


    @Test
    void testDeleteForumPost() {
        forumPostService.deleteForumPost(1L);
        verify(forumPostRepository, times(1)).deleteById(1L);
    }
}