package com.techpixe.getphoto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.getphoto.dto.PhotoGrapherDTo;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.AdminRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.serviceImpl.PhotoGrapherServiceImpl;


@ExtendWith(MockitoExtension.class)
class PhotoGrapherServiceImplTest {

	@Mock
    private PhotoGrapherRepository photoGrapherRepository;
    
	@Mock
    private AdminRepository adminRepository;
    private JavaMailSender javaMailSender;
    
    @InjectMocks
    private PhotoGrapherServiceImpl photoGrapherService;

   

    @Test
  public  void testRegistration() {
        long adminId = 1L;
        String email = "test@example.com";
        long mobileNumber = 1234567890L;
        String fullName = "Test Photographer";
        double subscriptionPlan = 50.0;
        long totalImages = 100L;

        // Mocking Admin retrieval
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(new Admin()));

        // Mocking save method
        when(photoGrapherRepository.save(any(PhotoGrapher.class))).thenAnswer(invocation -> {
            PhotoGrapher savedPhotoGrapher = invocation.getArgument(0);
            savedPhotoGrapher.setPhotographer_Id(1L); // Simulating generated ID
            return savedPhotoGrapher;
        });

        // Test the registration method
        PhotoGrapher savedPhotoGrapher = photoGrapherService.registration(adminId, email, mobileNumber, fullName,
                subscriptionPlan, totalImages);
        
        System.out.println("Excepted Email :"+email);
        
        System.out.println("Actual savedPhotoGrapher.getEmail() :"+savedPhotoGrapher.getMobileNumber());

        assertEquals(email, savedPhotoGrapher.getEmail());
        assertEquals(mobileNumber, savedPhotoGrapher.getMobileNumber());
        assertEquals(fullName, savedPhotoGrapher.getFullName());
        assertEquals(subscriptionPlan, savedPhotoGrapher.getSubcriptionPlan());
        assertEquals(totalImages, savedPhotoGrapher.getTotalImages());

        // Verify that the save method is called
        verify(photoGrapherRepository).save(any(PhotoGrapher.class));
    }

    @Test
  public  void testFetchById() {
        long id = 1L;
        
       // long id1 = 2l;
        
        PhotoGrapher photoGrapher = new PhotoGrapher();
        photoGrapher.setPhotographer_Id(id);

        // Mocking findById method
        when(photoGrapherRepository.findById(id)).thenReturn(Optional.of(photoGrapher));

        // Test the fetchById method
        PhotoGrapher fetchedPhotoGrapher = photoGrapherService.fetchById(id);
        
        assertEquals(id, fetchedPhotoGrapher.getPhotographer_Id());
    

      //  assertNotEquals(id, fetchedPhotoGrapher.getPhotographer_Id());
    }

    @Test
   public void testFetchAll() {
        List<PhotoGrapher> photoGraphers = new ArrayList<>();
        photoGraphers.add(new PhotoGrapher());
        photoGraphers.add(new PhotoGrapher());

        // Mocking findAll method
        when(photoGrapherRepository.findAll()).thenReturn(photoGraphers);

        // Test the fetchAll method
        List<PhotoGrapher> fetchedPhotoGraphers = photoGrapherService.fetchAll();

        assertEquals(2, fetchedPhotoGraphers.size());
        
       // assertNotEquals(3, fetchedPhotoGraphers.size());
    }

    @Test
   public  void testDeleteById() {
        long id = 1L;
        
       long id1 = 2l;

        // Test the deleteById method
        photoGrapherService.deleteById(id);

        // Verify that the deleteById method is called
        verify(photoGrapherRepository).deleteById(id);
    }

    @Test
   public  void testUpgradePlan() {
        long id = 1L;
        double subscriptionPlan = 60.0;
        long totalImages = 150L;
        PhotoGrapher photoGrapher = new PhotoGrapher();
        photoGrapher.setPhotographer_Id(id);
        
        
        //double subscriptionPlan1 = 40.0;
        

        // Mocking findById and save methods
        when(photoGrapherRepository.findById(id)).thenReturn(Optional.of(photoGrapher));
        when(photoGrapherRepository.save(any(PhotoGrapher.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test the upgradePlan method
        Optional<PhotoGrapher> upgradedPhotoGrapher = photoGrapherService.UpgradePlan(id, subscriptionPlan, totalImages);
        
        System.out.println("Excepted subscriptionPlan :"+subscriptionPlan);
        System.out.println("Actual upgradedPhotoGrapher.get().getSubcriptionPlan() :"+upgradedPhotoGrapher.get().getSubcriptionPlan());

        assertEquals(subscriptionPlan, upgradedPhotoGrapher.get().getSubcriptionPlan());
        assertEquals(totalImages, upgradedPhotoGrapher.get().getTotalImages());
    }

    // Add more test methods for other scenarios as needed
}

