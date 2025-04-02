package com.example.userservice;

import com.example.userservice.controller.SocialShareController;
import com.example.userservice.models.SocialShare;
import com.example.userservice.service.SocialShareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import com.example.userservice.exception.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocialShareControllerTest {

    @Mock
    private SocialShareService socialShareService;

    @InjectMocks
    private SocialShareController socialShareController;

    private SocialShare socialShare;

    @BeforeEach
    public void setUp() {
        socialShare = new SocialShare();
        socialShare.setShareId(1L);
        socialShare.setPlatform("Facebook");
    }

    @Test
    public void testGetAllSocialShares() {
        when(socialShareService.getAllSocialShares()).thenReturn(Arrays.asList(socialShare));

        assertNotNull(socialShareController.getAllSocialShares());
        assertEquals(1, socialShareController.getAllSocialShares().size());
    }

    @Test
    public void testGetSocialShareById_Found() {
        when(socialShareService.getSocialShareById(1L)).thenReturn(Optional.of(socialShare));

        ResponseEntity<SocialShare> response = socialShareController.getSocialShareById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Facebook", response.getBody().getPlatform());
    }

    @Test
public void testGetSocialShareById_NotFound() {
    when(socialShareService.getSocialShareById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> {
        socialShareController.getSocialShareById(1L);
    });
}


    @Test
    public void testCreateSocialShare() {
        when(socialShareService.createSocialShare(socialShare)).thenReturn(socialShare);

        ResponseEntity<SocialShare> response = socialShareController.createSocialShare(socialShare);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Facebook", response.getBody().getPlatform());
    }

    @Test
    public void testCreateSocialShare_InternalServerError() {
        when(socialShareService.createSocialShare(socialShare)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<SocialShare> response = socialShareController.createSocialShare(socialShare);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateSocialShare_Found() {
        when(socialShareService.updateSocialShare(1L, socialShare)).thenReturn(Optional.of(socialShare));

        ResponseEntity<SocialShare> response = socialShareController.updateSocialShare(1L, socialShare);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Facebook", response.getBody().getPlatform());
    }

    @Test
    public void testUpdateSocialShare_NotFound() {
        when(socialShareService.updateSocialShare(1L, socialShare)).thenReturn(Optional.empty());

        ResponseEntity<SocialShare> response = socialShareController.updateSocialShare(1L, socialShare);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteSocialShare_Found() {
        doNothing().when(socialShareService).deleteSocialShare(1L);

        ResponseEntity<HttpStatus> response = socialShareController.deleteSocialShare(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteSocialShare_InternalServerError() {
        doThrow(new RuntimeException("Error")).when(socialShareService).deleteSocialShare(1L);

        ResponseEntity<HttpStatus> response = socialShareController.deleteSocialShare(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
