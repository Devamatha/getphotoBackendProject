package com.techpixe.getphoto.serviceImpl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.getphoto.entity.Event;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.EventRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private PhotoGrapherRepository photoGrapherRepository;



	@Override
	public void deleteById(Long id)
	{
		eventRepository.deleteById(id);
		
	}

	@Override
	public Event fetchById(Long id)
	{
		
		return eventRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Id is not present"+id));
	}

	@Override
	public List<Event> fetchAll() 
	{
		
		List<Event> fetchAll = eventRepository.findAll();
		if (fetchAll.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"No events found");
		}
		return fetchAll;
	}

}
