package com.techpixe.getphoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
	@Autowired
	private EventService eventService;
	
	@PostMapping("/save/{photoGrapher}")

	public ResponseEntity<?> addRegisterion(@RequestParam String eventName,@RequestParam String eventAddress,@PathVariable Long photoGrapher) {
		try {
			Event registration = eventService.save(eventName, eventAddress, photoGrapher);

			return ResponseEntity.ok(registration);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Procesing in the request");

		}

	}
}
