package com.techpixe.getphoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techpixe.getphoto.entity.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	@Query(value = "SELECT event_Id FROM Event ORDER BY event_Id DESC LIMIT 1", nativeQuery = true)
	Long findTopId();
}
