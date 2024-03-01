package com.techpixe.getphoto.service;

import org.springframework.http.ResponseEntity;

import com.techpixe.getphoto.entity.Admin;

public interface AdminService {
	Admin registerAdmin(String fullName, String email, Long mobileNumber, String password);

	ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password);

	ResponseEntity<?> loginByEmail(String email, String password);
	
	ResponseEntity<?> changePassword(Long admin_Id ,String password, String confirmPassword);
	
	//********FORGOT PASSWORD************
	ResponseEntity<?> forgotPassword(String email);

}
