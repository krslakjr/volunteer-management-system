package com.example.notificationservice;

import com.example.notificationservice.controller.ForumPostController;
import com.example.notificationservice.models.ForumPost;
import com.example.notificationservice.service.ForumPostService;
import org.junit.jupiter.api.BeforeEach;
import com.example.notificationservice.exception.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.notificationservice.exception.*;

class ForumPostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ForumPostService forumPostService;

    @InjectMocks
    private ForumPostController forumPostController;

    private ForumPost forumPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(forumPostController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

        forumPost = new ForumPost();
        forumPost.setPostId(1L);
        forumPost.setContent("Test content");
        forumPost.setTimestamp(new Date());
    }

    @Test
    void testGetForumPostById_Found() throws Exception {
        when(forumPostService.getForumPostById(any(Long.class))).thenReturn(Optional.of(forumPost));

        mockMvc.perform(get("/forum-posts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(forumPost.getPostId()))
                .andExpect(jsonPath("$.content").value(forumPost.getContent()));

        verify(forumPostService, times(1)).getForumPostById(1L);
    }

    @Test
void testGetForumPostById_NotFound() throws Exception {

    when(forumPostService.getForumPostById(any(Long.class))).thenReturn(Optional.empty());

    mockMvc.perform(get("/forum-posts/{id}", 1L))
            .andExpect(status().isNotFound()) 
            .andExpect(jsonPath("$.message").value("ForumPost not found with id 1"));

    verify(forumPostService, times(1)).getForumPostById(1L);
}


    @Test
    void testCreateForumPost() throws Exception {
        when(forumPostService.createForumPost(any(ForumPost.class))).thenReturn(forumPost);

        mockMvc.perform(post("/forum-posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Test content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.postId").value(forumPost.getPostId()))
                .andExpect(jsonPath("$.content").value(forumPost.getContent()));

        verify(forumPostService, times(1)).createForumPost(any(ForumPost.class));
    }

    @Test
void testUpdateForumPost_NotFound() throws Exception {
    when(forumPostService.updateForumPost(any(Long.class), any(ForumPost.class)))
            .thenThrow(new ResourceNotFoundException("ForumPost not found with id 1", "id"));

    mockMvc.perform(put("/forum-posts/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"content\": \"Updated content\"}"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("ForumPost not found with id 1"));

    verify(forumPostService, times(1)).updateForumPost(any(Long.class), any(ForumPost.class));
}


    @Test
    void testDeleteForumPost() throws Exception {
        doNothing().when(forumPostService).deleteForumPost(any(Long.class));

        mockMvc.perform(delete("/forum-posts/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(forumPostService, times(1)).deleteForumPost(any(Long.class));
    }
}