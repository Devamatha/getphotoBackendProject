package com.techpixe.getphoto.service;

import java.io.IOException;
import java.sql.Date;

import com.techpixe.getphoto.entity.Event;

public interface EventService {
	Event save(String eventName, String eventAddress,Date eventDate, Long photoGrapher)throws IOException ;

}
