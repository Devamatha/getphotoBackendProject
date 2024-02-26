package com.techpixe.getphoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.getphoto.entity.Images;

public interface ImagesRepository extends JpaRepository<Images, Long> {

	// Optional<Images> findAll(Long id);

//	List<byte[]> getAllImages();

	// List<Images> getAllImages();

}
