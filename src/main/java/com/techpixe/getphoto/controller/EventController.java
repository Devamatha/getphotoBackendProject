package com.techpixe.getphoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
	@Autowired
	private EventService eventService;
}
