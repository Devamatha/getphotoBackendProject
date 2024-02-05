package com.techpixe.getphoto.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.techpixe.getphoto.entity.PhotoGrapher;

public interface PhotoGrapherService {
	PhotoGrapher registration(Long admin, String email, Long mobileNumber, String fullName);

	ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password);

	ResponseEntity<?> loginByEmail(String email, String password);

	PhotoGrapher fetchById(Long id);

	void deleteById(Long id);

	List<PhotoGrapher> fetchAll();

	// Optional<PhotoGrapher> update(Long id, PhotoGrapher photoGrapher);

}
