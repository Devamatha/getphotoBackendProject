package com.techpixe.getphoto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.EventRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.EventService;
import com.techpixe.getphoto.serviceImpl.EventServiceImpl;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

	@Mock
    private EventRepository eventRepository;
    
	@Mock
    private PhotoGrapherRepository photoGrapherRepository;
    
    @InjectMocks
    private EventServiceImpl eventService;

   
    @Test
   public void testSave() throws IOException {
        String eventName = "Test Event";
        String eventAddress = "Test Address";
        Date eventDate = new Date(System.currentTimeMillis());
        Long photoGrapherId = 1L;

        // Mocking PhotoGrapher retrieval
        when(photoGrapherRepository.findById(photoGrapherId)).thenReturn(Optional.of(new PhotoGrapher()));

        // Mocking save method
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
            Event savedEvent = invocation.getArgument(0);
            savedEvent.setEvent_Id(1L); // Simulating generated ID
            return savedEvent;
        });

        // Test the save method
        Event savedEvent = eventService.save(eventName, eventAddress, eventDate, photoGrapherId);
        
        System.out.println("Excepted :"+eventName);
        System.out.println("Actual savedEvent.getEventName():"+savedEvent.getEventName());
        
        assertEquals(eventName, savedEvent.getEventName());
        assertEquals(eventAddress, savedEvent.getEventAddress());
        assertEquals(eventDate, savedEvent.getEventDate());


        // Verify that the save method is called
        verify(eventRepository).save(any(Event.class));
    }
    
    
    
    @Test
    public void testSave1() throws IOException {
         String eventName = "Test Event";
         String eventAddress = "Test Address";
         Date eventDate = new Date(System.currentTimeMillis());
         Long photoGrapherId = 1L;

         // Mocking PhotoGrapher retrieval
         when(photoGrapherRepository.findById(photoGrapherId)).thenReturn(Optional.of(new PhotoGrapher()));

         // Mocking save method
         when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
             Event savedEvent = invocation.getArgument(0);
             savedEvent.setEvent_Id(1L); // Simulating generated ID
             return savedEvent;
         });

         // Test the save method
         Event savedEvent = eventService.save(eventName, eventAddress, eventDate, photoGrapherId);
         
         System.out.println("Excepted :"+eventName);
         System.out.println("Actual savedEvent.getEventName():"+savedEvent.getEventName());

//         assertNotEquals(eventName, savedEvent.getEventName());
//         assertNotEquals(eventAddress, savedEvent.getEventAddress());
//         assertNotEquals(eventDate, savedEvent.getEventDate());
         
//         assertEquals(eventName, savedEvent.getEventName());
//         assertEquals(eventAddress, savedEvent.getEventAddress());
//         assertEquals(eventDate, savedEvent.getEventDate());
         
         assertNotNull(eventName, savedEvent.getEventName());
         assertNotNull(eventAddress, savedEvent.getEventAddress());
         assertEquals(eventDate, savedEvent.getEventDate());


         // Verify that the save method is called
         verify(eventRepository).save(any(Event.class));
     }

    @Test
  public void testFetchById() {
        long id = 1L;
        
        long id1=2l;
        
        Event event = new Event();
        event.setEvent_Id(id);

        // Mocking findById method
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        // Test the fetchById method
        Event fetchedEvent = eventService.fetchById(id);
        
        System.out.println("Excepted :"+id);
        System.out.println("Actual fetchedEvent.getEvent_Id() :"+fetchedEvent.getEvent_Id());

        assertEquals(id, fetchedEvent.getEvent_Id());
        
      //  assertNotEquals(id, fetchedEvent.getEvent_Id());
    }

    @Test
   public void testFetchAll() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        events.add(new Event());

        // Mocking findAll method
        when(eventRepository.findAll()).thenReturn(events);

        // Test the fetchAll method
        List<Event> fetchedEvents = eventService.fetchAll();
        
        System.out.println("Excepted :"+2);
        System.out.println("Actual fetchedEvents.size() :"+fetchedEvents.size());

        assertEquals(2, fetchedEvents.size());
        
      //  assertNotEquals(4, fetchedEvents.size());
        
        
    }

    @Test
   public void testUpdate() {
        long id = 1L;
        String eventName = "Updated Event Name";
        String eventAddress = "Updated Event Address";
        Date eventDate = new Date(System.currentTimeMillis());

        Event event = new Event();
        event.setEvent_Id(id);

        // Mocking findById and save methods
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test the update method
        Optional<Event> updatedEvent = eventService.update(eventName, eventAddress, eventDate, id);

        assertEquals(eventName, updatedEvent.get().getEventName());
        assertEquals(eventAddress, updatedEvent.get().getEventAddress());
        assertEquals(eventDate, updatedEvent.get().getEventDate());
    }

    @Test
    public void testDeleteById() {
        long id = 1L;

       // long id1=2l;
        
        // Test the deleteById method
        eventService.deleteById(id);

        // Verify that the deleteById method is called
        verify(eventRepository).deleteById(id);
    }
}

