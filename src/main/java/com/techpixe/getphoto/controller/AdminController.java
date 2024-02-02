package com.techpixe.getphoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.dto.ErrorResponseDto;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController 
{
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/registration")
	public ResponseEntity<Admin> registerAdmin(@RequestParam String fullName,@RequestParam String email,@RequestParam Long mobileNumber,@RequestParam String password)
	{
		Admin registeredAdmin = adminService.registerAdmin(fullName,email,mobileNumber,password);
		return new ResponseEntity<Admin>(registeredAdmin, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String emailOrMobileNumber, @RequestParam String password) {
		if (emailOrMobileNumber != null) 
		{
			if (isEmail(emailOrMobileNumber)) 
			{
				return adminService.loginByEmail(emailOrMobileNumber, password);
			} 
			else if (isMobileNumber(emailOrMobileNumber)) 
			{
				return adminService.loginByMobileNumber(Long.parseLong(emailOrMobileNumber), password);
			} 
			else 
			{
				ErrorResponseDto errorResponse = new ErrorResponseDto();
				errorResponse.setError("Invalid emailOrMobileNumber format. Please provide a valid email or mobile number.");
				return ResponseEntity.internalServerError().body(errorResponse);
			}
		} 
		else 
		{
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid input. Email or mobile number must be provided.");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
		
	}

	private boolean isEmail(String emailOrMobileNumber)
	{
		return emailOrMobileNumber.contains("@");
	}

	private boolean isMobileNumber(String emailOrMobileNumber) 
	{
		return emailOrMobileNumber.matches("\\d+");
	}
}
