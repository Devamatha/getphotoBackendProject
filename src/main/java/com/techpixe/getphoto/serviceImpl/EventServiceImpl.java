package com.techpixe.getphoto.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.repository.EventRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.EventService;

@Service
public class EventServiceImpl implements EventService
{
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private PhotoGrapherRepository photoGrapherRepository;
	
	@Override
	public Event save(String eventName, String eventAddress, Long photoGrapher) 
	{
		Event event=new Event();
		 return eventRepository.save(event);
		
	}

}
