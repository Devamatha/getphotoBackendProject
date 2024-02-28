package com.techpixe.getphoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.entity.PhotoGrapher;

public interface PhotoGrapherRepository extends JpaRepository<PhotoGrapher, Long> {
	PhotoGrapher findByEmail(String email);

	PhotoGrapher findByMobileNumber(Long mobileNumber);

	PhotoGrapher findByPassword(String password);
}
