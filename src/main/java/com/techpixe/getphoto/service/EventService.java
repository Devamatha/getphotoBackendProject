package com.techpixe.getphoto.service;

import java.io.IOException;

import com.techpixe.getphoto.entity.Event;

public interface EventService {
	Event save(String eventName, String eventAddress, Long photoGrapher)throws IOException ;

}
