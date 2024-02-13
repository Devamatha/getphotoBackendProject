package com.techpixe.getphoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.getphoto.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
