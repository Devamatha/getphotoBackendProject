package com.techpixe.getphoto.service;

import org.springframework.http.ResponseEntity;

import com.techpixe.getphoto.entity.PhotoGrapher;

public interface PhotoGrapherService 
{
	PhotoGrapher registration(Long admin ,String email,Long mobileNumber,String fullName);
	ResponseEntity<?> loginByMobileNumber(Long mobileNumber,String password);
	ResponseEntity<?> loginByEmail(String email, String password);
	
}
