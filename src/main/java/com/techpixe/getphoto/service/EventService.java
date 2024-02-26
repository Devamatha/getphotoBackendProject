package com.techpixe.getphoto.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.techpixe.getphoto.entity.Event;

import java.sql.Date;

public interface EventService {
	Event save(String eventName, String eventAddress, Date eventDate, Long photoGrapher) throws IOException;

	Event fetchById(Long id);

	List<Event> fetchAll();

	void deleteById(Long id);

	Optional<Event> update(String eventName, String eventAddress,Date eventDate, Long id);
}
