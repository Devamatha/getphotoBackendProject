package com.techpixe.getphoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.dto.ErrorResponseDto;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.service.PhotoGrapherService;

@RestController
@RequestMapping("/client")
public class PhotoGrapherController 
{
	@Autowired
	private PhotoGrapherService photoGrapherService;
	@PostMapping("/registration/{admin}")
	public ResponseEntity<?>addRegisterion(@PathVariable Long admin,@RequestParam String email,@RequestParam Long mobileNumber,@RequestParam String fullName){
		try {
		PhotoGrapher registration=photoGrapherService.registration(admin,email,mobileNumber,fullName);
		
		return ResponseEntity.ok(registration);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Procesing in the request");
			
		}
		
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String emailOrMobileNumber, @RequestParam String password) {
		if (emailOrMobileNumber != null) 
		{
			if (isEmail(emailOrMobileNumber)) 
			{
				return photoGrapherService.loginByEmail(emailOrMobileNumber, password);
			} 
			else if (isMobileNumber(emailOrMobileNumber)) 
			{
				return photoGrapherService.loginByMobileNumber(Long.parseLong(emailOrMobileNumber), password);
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
