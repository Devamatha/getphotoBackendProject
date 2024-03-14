package com.techpixe.getphoto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techpixe.getphoto.dto.OtpResponseDto;
import com.techpixe.getphoto.dto.OtpValidationRequest;
import com.techpixe.getphoto.entity.OtpRequest;
import com.techpixe.getphoto.service.SmsService;

@RestController
@RequestMapping("/otp")
public class OtpController {

	@Autowired
	private SmsService smsService;

	@GetMapping("/process")
	public String processSMS() {
		return "SMS sent";
	}

	@PostMapping("/send-otp")
	public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
		return smsService.sendSMS(otpRequest);
	}

	@PostMapping("/validate-otp")
	public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
		return smsService.validateOtp(otpValidationRequest);
	}

}