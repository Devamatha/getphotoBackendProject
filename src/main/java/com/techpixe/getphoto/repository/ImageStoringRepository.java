package com.techpixe.getphoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.getphoto.entity.ImageStoring;

public interface ImageStoringRepository extends JpaRepository<ImageStoring, Long> {

}
