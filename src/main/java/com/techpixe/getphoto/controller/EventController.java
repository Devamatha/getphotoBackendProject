package com.techpixe.getphoto.controller;

import java.util.List;
import java.util.Optional;
import java.sql.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.ImageStoring;
import com.techpixe.getphoto.service.EventService;
import com.techpixe.getphoto.util.ImageUtils;

@RestController
@RequestMapping("/event")
public class EventController {
	@Autowired
	private EventService eventService;
	public static final Logger logger = Logger.getLogger(EventController.class);

	@GetMapping("/get/{event_Id}")
	public ResponseEntity<?> fetchByEventId(@PathVariable("event_Id") Long id) {

		logger.debug("Event Id is Present" + id);

		Event fetchById = eventService.fetchById(id);
		for (ImageStoring image : fetchById.getImages()) {

			byte[] decompressedImage = ImageUtils.decompressImage(image.getImage());
			logger.debug("Hloo world");
			logger.error("eroro");
			logger.info("Hlo hhhadsd");
			image.setImage(decompressedImage);
		}
		return ResponseEntity.ok(fetchById);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<?>> fetchAll() {

		logger.debug("All Events are Present");

		List<Event> fetchAll = eventService.fetchAll();
		return ResponseEntity.ok(fetchAll);

	}

	@DeleteMapping("/delete/{event_Id}")
	public ResponseEntity<Void> deleteById(@PathVariable("event_Id") Long id) {
		Event event = eventService.fetchById(id);
		if (event == null) {

			logger.debug("Event is not Found ");

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {

			logger.debug("Event is Deleted");

			eventService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@PostMapping("/save/{photoGrapher}")

	public ResponseEntity<?> addRegisterion(@RequestParam String eventName, @RequestParam String eventAddress,
			@RequestParam Date eventDate, @PathVariable Long photoGrapher) {
		try {

			logger.debug("Event Registration is Succesfull");
			logger.info("Event Registration has done based on PhotoGrapher ID");

			Event registration = eventService.save(eventName, eventAddress, eventDate, photoGrapher);

			return ResponseEntity.ok(registration);

		} catch (Exception e) {

			logger.error("Event Registration is not Succesfull");

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Procesing in the request");

		}

	}

	@PutMapping("/update/{event_Id}")
	public ResponseEntity<Event> update(@RequestParam(required = false) String eventName,
			@RequestParam(required = false) String eventAddress, @RequestParam(required = false) Date eventDate,
			@PathVariable("event_Id") Long id) {

		logger.debug("Event is pdated Succesfully");

		Optional<Event> updatedEvent = eventService.update(eventName, eventAddress, eventDate, id);
		return updatedEvent.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}
