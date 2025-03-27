package com.example.userservice;

import com.example.userservice.service.SocialShareService;
import com.example.userservice.models.SocialShare;
import com.example.userservice.repository.SocialShareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocialShareServiceTest {

    @Mock
    private SocialShareRepository socialShareRepository;

    @InjectMocks
    private SocialShareService socialShareService;

    private SocialShare socialShare;

    @BeforeEach
    public void setUp() {
        socialShare = new SocialShare();
        socialShare.setShareId(1L);
        socialShare.setPlatform("Facebook");
    }

    @Test
    public void testGetAllSocialShares() {
        when(socialShareRepository.findAll()).thenReturn(Arrays.asList(socialShare));

        assertEquals(1, socialShareService.getAllSocialShares().size());
        assertEquals("Facebook", socialShareService.getAllSocialShares().get(0).getPlatform());
    }

    @Test
    public void testGetSocialShareById_Found() {
        when(socialShareRepository.findById(1L)).thenReturn(Optional.of(socialShare));

        Optional<SocialShare> response = socialShareService.getSocialShareById(1L);
        assertTrue(response.isPresent());
        assertEquals("Facebook", response.get().getPlatform());
    }

    @Test
    public void testGetSocialShareById_NotFound() {
        when(socialShareRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<SocialShare> response = socialShareService.getSocialShareById(1L);
        assertFalse(response.isPresent());
    }

    @Test
    public void testCreateSocialShare() {
        when(socialShareRepository.save(socialShare)).thenReturn(socialShare);

        SocialShare response = socialShareService.createSocialShare(socialShare);
        assertNotNull(response);
        assertEquals("Facebook", response.getPlatform());
    }

    @Test
    public void testUpdateSocialShare_Found() {
        when(socialShareRepository.existsById(1L)).thenReturn(true);
        when(socialShareRepository.save(socialShare)).thenReturn(socialShare);

        Optional<SocialShare> response = socialShareService.updateSocialShare(1L, socialShare);
        assertTrue(response.isPresent());
        assertEquals("Facebook", response.get().getPlatform());
    }

    @Test
    public void testUpdateSocialShare_NotFound() {
        when(socialShareRepository.existsById(1L)).thenReturn(false);

        Optional<SocialShare> response = socialShareService.updateSocialShare(1L, socialShare);
        assertFalse(response.isPresent());
    }

    @Test
    public void testDeleteSocialShare() {
        doNothing().when(socialShareRepository).deleteById(1L);

        socialShareService.deleteSocialShare(1L);
        verify(socialShareRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSocialShare_NotFound() {
        doThrow(new RuntimeException("Error")).when(socialShareRepository).deleteById(1L);

        try {
            socialShareService.deleteSocialShare(1L);
            fail("Expected exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Error", e.getMessage());
        }
    }
}
