package com.techpixe.getphoto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.service.EventService;


@RestController
@RequestMapping("/event")
public class EventController 
{
	@Autowired
	private EventService eventService;

	
	
	@GetMapping("/get/{event_Id}")
	public ResponseEntity<?> fetchByEventId(@PathVariable ("event_Id") Long id)
	{
		Event fetchById = eventService.fetchById(id);
		return ResponseEntity.ok(fetchById);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<?>> fetchAll()
	{
		List<Event> fetchAll = eventService.fetchAll();
		return ResponseEntity.ok(fetchAll);
	}
	
	@DeleteMapping("/delete/{event_Id}")
	public ResponseEntity<Void> deleteById(@PathVariable("event_Id") Long id)
	{
		Event event = eventService.fetchById(id);
		if (event==null) 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			eventService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	
	
	
}
