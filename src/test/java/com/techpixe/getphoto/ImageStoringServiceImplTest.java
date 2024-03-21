package com.techpixe.getphoto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.ImageStoring;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.EventRepository;
import com.techpixe.getphoto.repository.ImageStoringRepository;
import com.techpixe.getphoto.serviceImpl.ImageStoringServiceImpl;
import com.techpixe.getphoto.util.ImageUtils;

@ExtendWith(MockitoExtension.class)
public class ImageStoringServiceImplTest {

    @Mock
    private ImageStoringRepository imageStoringRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private ImageStoringServiceImpl imageStoringService;

    @Test
    public void testUploadImage_Success() throws IOException {
        // Mock data
        Long eventId = 1L;
        byte[] imageData = new byte[] { 0x01, 0x02, 0x03 }; // Sample image data
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", imageData);
        Event mockEvent = new Event();
        mockEvent.setEvent_Id(eventId);
        PhotoGrapher mockPhotographer = new PhotoGrapher();
        mockPhotographer.setTotalImages(10L);

        // Stubbing repository methods
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));
        when(imageStoringRepository.save(any(ImageStoring.class))).thenAnswer(invocation -> {
            ImageStoring imageDataArg = invocation.getArgument(0);
            imageDataArg.setId(1L); // Assigning some ID for the saved image
            return imageDataArg;
        });

        // Call the service method
        ResponseEntity<?> response = imageStoringService.uploadImage(eventId, mockMultipartFile);

        // Verify the response
        assert response.getStatusCode().equals(HttpStatus.OK);
        assert response.getBody() instanceof ImageStoring;
    }

    @Test
    public void testUploadImage_EventNotFound() throws IOException {
        // Mock data
        Long eventId = 1L;
        byte[] imageData = new byte[] { 0x01, 0x02, 0x03 }; // Sample image data
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", imageData);

        // Stubbing repository methods
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Call the service method
        ResponseEntity<?> response = imageStoringService.uploadImage(eventId, mockMultipartFile);

        // Verify the response
        assert response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR);
        assert response.getBody().equals("Event with ID " + eventId + " not found");
    }

    // Add more test cases for other scenarios as needed
}

