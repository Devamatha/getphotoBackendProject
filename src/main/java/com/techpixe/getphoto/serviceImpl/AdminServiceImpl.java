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

import com.techpixe.getphoto.dto.AdminDto;
import com.techpixe.getphoto.dto.ErrorResponseDto;
import com.techpixe.getphoto.dto.PhotoGrapherDTo;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.AdminRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private PhotoGrapherRepository PhotoGrapherRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("$(spring.mail.username)")
	private String fromMail;

	@Override
	public Admin registerAdmin(String fullName, String email, Long mobileNumber, String password) {
		Admin admin = new Admin();
		admin.setFullName(fullName);
		admin.setEmail(email);
		admin.setMobileNumber(mobileNumber);
		admin.setPassword(password);
		admin.setRole("admin");
		return adminRepository.save(admin);
	}

	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password) {
		Admin user = adminRepository.findByMobileNumber(mobileNumber);
		PhotoGrapher user1 = PhotoGrapherRepository.findByMobileNumber(mobileNumber);

		if (user != null && user.getPassword().equals(password)) {
			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			applicationFormDTo.setRole(user.getRole());

			return ResponseEntity.ok(applicationFormDTo);
		} else if (user1 != null && user1.getPassword().equals(password)) {
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user1.getPhotographer_Id());
			photoGrapherDTo.setFullName(user1.getFullName());
			photoGrapherDTo.setEmail(user1.getEmail());
			photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
			photoGrapherDTo.setPassword(user1.getPassword());
			photoGrapherDTo.setRole(user1.getRole());
			return ResponseEntity.ok(photoGrapherDTo);
		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid mobile number or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@Override
	public ResponseEntity<?> loginByEmail(String email, String password) {
		Admin user = adminRepository.findByEmail(email);
		PhotoGrapher user1 = PhotoGrapherRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			applicationFormDTo.setRole(user.getRole());
			return ResponseEntity.ok(applicationFormDTo);

		} else if (user1 != null && user1.getPassword().equals(password)) {
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user1.getPhotographer_Id());
			photoGrapherDTo.setFullName(user1.getFullName());
			photoGrapherDTo.setEmail(user1.getEmail());
			photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
			photoGrapherDTo.setPassword(user1.getPassword());
			photoGrapherDTo.setRole(user1.getRole());

			return ResponseEntity.ok(photoGrapherDTo);
		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid email or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	//***************CHANGE PASSWORD*************************	

		@Override
		public ResponseEntity<?> changePassword(Long admin_Id, String password, String confirmPassword) {
			Admin user = adminRepository.findById(admin_Id).orElse(null);
			PhotoGrapher user1 = PhotoGrapherRepository.findById(admin_Id)
			        .orElseThrow(() -> new RuntimeException("photographer is not present: " + admin_Id));
			if (user != null && user.getPassword().equals(password)) {

				AdminDto adminDto = new AdminDto();
				adminDto.setAdmin_Id(user.getAdmin_Id());
				adminDto.setFullName(user.getFullName());
				adminDto.setEmail(user.getEmail());
				adminDto.setMobileNumber(user.getMobileNumber());
				adminDto.setRole(user.getRole());
				adminDto.setPassword(confirmPassword);

				user.setPassword(confirmPassword);
				adminRepository.save(user);
				return ResponseEntity.ok(adminDto);

			} else if (user1 != null && user1.getPassword().equals(password)) {

				PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
				photoGrapherDTo.setPhotographer_Id(user1.getPhotographer_Id());
				photoGrapherDTo.setFullName(user1.getFullName());
				photoGrapherDTo.setEmail(user1.getEmail());
				photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
				photoGrapherDTo.setPassword(confirmPassword);
				photoGrapherDTo.setRole(user1.getRole());

				user1.setPassword(confirmPassword);
				PhotoGrapherRepository.save(user1);
				return ResponseEntity.ok(photoGrapherDTo);
			} else {
				ErrorResponseDto error = new ErrorResponseDto();
				error.setError("######################Password is not present#################");
				// return ResponseEntity.internalServerError().body(error);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
			}
		}

		// *************FORGOT PASSWORD*****************

		@Override
		public ResponseEntity<?> forgotPassword(String email) {
			Admin admin = adminRepository.findByEmail(email);
			PhotoGrapher photoGrapher = PhotoGrapherRepository.findByEmail(email);

			if (admin != null) {
				AdminDto adminDTO = new AdminDto();
				adminDTO.setAdmin_Id(admin.getAdmin_Id());
				adminDTO.setFullName(admin.getFullName());
				adminDTO.setEmail(email);
				adminDTO.setMobileNumber(admin.getMobileNumber());
				adminDTO.setRole(admin.getRole());

				String password = generatePassword();
				adminDTO.setPassword(password);
				admin.setPassword(password);
				adminRepository.save(admin);

				return ResponseEntity.ok(adminDTO);
			} else if (photoGrapher != null) {
				PhotoGrapherDTo photoGrapherDTO = new PhotoGrapherDTo();
				photoGrapherDTO.setPhotographer_Id(photoGrapher.getPhotographer_Id());
				photoGrapherDTO.setFullName(photoGrapher.getFullName());
				photoGrapherDTO.setEmail(email);
				photoGrapherDTO.setMobileNumber(photoGrapher.getMobileNumber());
				photoGrapherDTO.setRole(photoGrapher.getRole());

				String password = generatePassword();
				photoGrapherDTO.setPassword(password);
				photoGrapher.setPassword(password);
				PhotoGrapherRepository.save(photoGrapher);
				
				SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
				simpleMailMessage.setFrom(fromMail);
				simpleMailMessage.setTo(email);
				simpleMailMessage.setSubject("Registration completed Successfully in GetPhoto application\n");
				simpleMailMessage.setText("Dear " +photoGrapher.getFullName()
						+ "\n\nPlease check your  email and generted passowrd\n UserEmail  :"
						+ email + "\n  MobileNumber :" + photoGrapher.getMobileNumber() + "\n New Password   :" + password
						+ "\n\n"
						+ "you will be required to reset the New password upon login\n\n\n if you have any question or if you would like to request a call-back,please email us at support info@techpixe.com");
				javaMailSender.send(simpleMailMessage);

				return ResponseEntity.ok(photoGrapherDTO);
			} else {
				ErrorResponseDto errorResponseDto = new ErrorResponseDto();
				errorResponseDto.setError("Email is not matching");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
			}
		}

	// **********Generate Random Password ********************
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC_STRING = "0123456789";

	public static String generatePassword() {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(index));
		}
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(index));
		}

		return builder.toString();
	}

}
