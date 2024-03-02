package com.techpixe.getphoto.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.dto.ErrorResponseDto;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	private static final Logger logger = Logger.getLogger(AdminController.class);

	@PostMapping("/registration")
	public ResponseEntity<Admin> registerAdmin(@RequestParam String fullName, @RequestParam String email,
			@RequestParam Long mobileNumber, @RequestParam String password) {

		logger.debug("Registration is Successful");
		logger.info("request goes to controller to service Class");

		Admin registeredAdmin = adminService.registerAdmin(fullName, email, mobileNumber, password);
		return new ResponseEntity<Admin>(registeredAdmin, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String emailOrMobileNumber, @RequestParam String password) {
		if (emailOrMobileNumber != null) {
			if (isEmail(emailOrMobileNumber)) {
				return adminService.loginByEmail(emailOrMobileNumber, password);
			} else if (isMobileNumber(emailOrMobileNumber)) {
				return adminService.loginByMobileNumber(Long.parseLong(emailOrMobileNumber), password);
			} else {
				ErrorResponseDto errorResponse = new ErrorResponseDto();
				errorResponse
						.setError("Invalid emailOrMobileNumber format. Please provide a valid email or mobile number.");
				return ResponseEntity.internalServerError().body(errorResponse);
			}
		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid input. Email or mobile number must be provided.");
			return ResponseEntity.internalServerError().body(errorResponse);
		}

	}

	private boolean isEmail(String emailOrMobileNumber) {
		return emailOrMobileNumber.contains("@");
	}

	private boolean isMobileNumber(String emailOrMobileNumber) {
		return emailOrMobileNumber.matches("\\d+");
	}

	// ************Change Password***************
	@PostMapping("/changepassword/{admin_Id}")
	public ResponseEntity<?> changePassword(@PathVariable Long admin_Id, @RequestParam String password,
			@RequestParam String confirmPassword) {
		if (password != null && confirmPassword != null) {
			return adminService.changePassword(admin_Id, password, confirmPassword);
		} else {
			ErrorResponseDto error = new ErrorResponseDto();
			error.setError("*********Password is not present*************");
			return ResponseEntity.internalServerError().body(error);
		}
	}

	// *************FORGOT PASSWORD****************
	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestParam String email) {
		if (email != null) {
			if (isEmail(email)) {
				return adminService.forgotPassword(email);
			} else {
				ErrorResponseDto error = new ErrorResponseDto();
				error.setError("*********Invalid Email Pattern *************");
				return ResponseEntity.internalServerError().body(error);
			}
		} else {
			ErrorResponseDto error = new ErrorResponseDto();
			error.setError("*********Email is not present*************");
			return ResponseEntity.internalServerError().body(error);
		}
	}

}
