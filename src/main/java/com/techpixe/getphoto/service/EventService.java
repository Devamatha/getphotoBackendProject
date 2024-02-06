package com.techpixe.getphoto.service;

import java.util.List;

import com.techpixe.getphoto.entity.Event;

public interface EventService
{
	
	Event fetchById(Long id);
	
	List<Event> fetchAll();
	
	void deleteById(Long id);

}
