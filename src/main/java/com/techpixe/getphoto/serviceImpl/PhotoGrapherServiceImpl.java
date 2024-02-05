package com.techpixe.getphoto.serviceImpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techpixe.getphoto.dto.ErrorResponseDto;
import com.techpixe.getphoto.dto.PhotoGrapherDTo;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.AdminRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.PhotoGrapherService;

@Service
public class PhotoGrapherServiceImpl implements PhotoGrapherService {
	@Autowired
	private PhotoGrapherRepository PhotoGrapherRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("$(spring.mail.username)")
	private String fromMail;

	@Override
	public PhotoGrapher registration(Long admin, String email, Long mobileNumber, String fullName) {
		Admin admin2 = adminRepository.findById(admin)
				.orElseThrow(() -> new RuntimeException("Id is not present" + admin));
		if (admin2 != null) {
			System.out.println("id is  present" + admin2);
			PhotoGrapher photoGrapher = new PhotoGrapher();
			photoGrapher.setAdmin(admin2);
			photoGrapher.setEmail(email);
			photoGrapher.setMobileNumber(mobileNumber);
			photoGrapher.setFullName(fullName);
           photoGrapher.setRole("photographer");
			String password = generatePassword();
			photoGrapher.setPassword(password);
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(fromMail);
			simpleMailMessage.setTo(email);
			

			simpleMailMessage.setSubject("Registration completed Successfully in GetPhoto application\n");
			simpleMailMessage.setText("Dear " + fullName
					+ ",\n\n Thank you for singing Up for GetPhoto! click bewlow to get  started on your web or mobile device .\n\nPlease check your registered email and generted passowrd\n UserEmail  :"
					+ email + "\n Registered MobileNumber :" + mobileNumber + "\n Temporary Password   :" + password
					+ "\n\n"
					+ "you will be required to reset the temporary password upon login\n\n\n if you have any question or if you would like to request a call-back,please email us at support info@techpixe.com");
			javaMailSender.send(simpleMailMessage);
			return PhotoGrapherRepository.save(photoGrapher);
		} else {
			System.out.println("id is not present");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin with this Id is not present" + admin);
		}

	}

	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String DIGITS = "0123456789";

	public static String generatePassword() {
		Random random = new Random();

		StringBuilder lettersBuilder = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(LETTERS.length());
			lettersBuilder.append(LETTERS.charAt(index));
		}

		StringBuilder digitsBuilder = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(DIGITS.length());
			digitsBuilder.append(DIGITS.charAt(index));
		}

		return lettersBuilder.toString() + digitsBuilder.toString();
	}
	
	
	
	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password)
	{
		PhotoGrapher user = PhotoGrapherRepository.findByMobileNumber(mobileNumber);

		if (user != null && user.getPassword().equals(password))
		{
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user.getPhotographer_Id());
			photoGrapherDTo.setFullName(user.getFullName());
			photoGrapherDTo.setEmail(user.getEmail());
			photoGrapherDTo.setMobileNumber(user.getMobileNumber());
			photoGrapherDTo.setPassword(user.getPassword());
			return ResponseEntity.ok(photoGrapherDTo);
		}
		else 
		{
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid mobile number or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@Override
	public ResponseEntity<?> loginByEmail(String email, String password) 
	{
		PhotoGrapher user = PhotoGrapherRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password))
		{
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user.getPhotographer_Id());
			photoGrapherDTo.setFullName(user.getFullName());
			photoGrapherDTo.setEmail(user.getEmail());
			photoGrapherDTo.setMobileNumber(user.getMobileNumber());
			photoGrapherDTo.setPassword(user.getPassword());
			return ResponseEntity.ok(photoGrapherDTo);

		} 
		else 
		{
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid email or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

}
