package com.techpixe.getphoto.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.apache.log4j.Logger;
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

	private static final Logger logger = Logger.getLogger(PhotoGrapherServiceImpl.class);

	@Autowired
	private PhotoGrapherRepository photoGrapherRepository;

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

			logger.debug("Photographer Registration is successfull based on Admin");
			logger.info(
					"Request comes from PhotoGrapherController to PhotoGrapherServiceImpl through PhotoGrapher Service");

			System.out.println("id is  present" + admin2);
			PhotoGrapher photoGrapher = new PhotoGrapher();
			photoGrapher.setAdmin(admin2);
			photoGrapher.setEmail(email);
			photoGrapher.setMobileNumber(mobileNumber);
			photoGrapher.setFullName(fullName);
			photoGrapher.setRole("photographer");
			photoGrapher.setRegistrationDate(LocalDate.now());
			String password = generatePassword();
			photoGrapher.setPassword(password);

			photoGrapher = photoGrapherRepository.save(photoGrapher);
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
			return photoGrapher;
		} else {

			logger.error("Registration is Unsuccessfull. Admin Id is not Present");

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
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password) {
		PhotoGrapher user = photoGrapherRepository.findByMobileNumber(mobileNumber);

		if (user != null && user.getPassword().equals(password)) {
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user.getPhotographer_Id());
			photoGrapherDTo.setFullName(user.getFullName());
			photoGrapherDTo.setEmail(user.getEmail());
			photoGrapherDTo.setMobileNumber(user.getMobileNumber());
			photoGrapherDTo.setPassword(user.getPassword());
			return ResponseEntity.ok(photoGrapherDTo);
		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid mobile number or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@Override
	public ResponseEntity<?> loginByEmail(String email, String password) {
		PhotoGrapher user = photoGrapherRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user.getPhotographer_Id());
			photoGrapherDTo.setFullName(user.getFullName());
			photoGrapherDTo.setEmail(user.getEmail());
			photoGrapherDTo.setMobileNumber(user.getMobileNumber());
			photoGrapherDTo.setPassword(user.getPassword());
			return ResponseEntity.ok(photoGrapherDTo);

		} else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid email or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

	@Override
	public PhotoGrapher fetchById(Long id) {

		return photoGrapherRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("PhotoGrapher Id '" + id + "' is not present "));
	}

	@Override
	public List<PhotoGrapher> fetchAll() {
		List<PhotoGrapher> fetchAll = photoGrapherRepository.findAll();
		if (fetchAll.isEmpty()) {

			logger.error("No PhotoGraphers Present");

			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, " No PhotoGraphers found");
		}
		logger.debug("Photographers are Found");
		logger.info("Request comes from PhotoGrapher Controller to PhotoGrapher Service through SErvice");

		return fetchAll;
	}

	@Override
	public void deleteById(Long id) {
		photoGrapherRepository.deleteById(id);
	}

	@Override
	public Optional<PhotoGrapher> update(Long id, String email, Long mobileNumber, String password, String fullName) {
		return photoGrapherRepository.findById(id).map(existingPhotoGrapher -> {
			existingPhotoGrapher.setFullName(fullName != null ? fullName : existingPhotoGrapher.getFullName());
			existingPhotoGrapher.setEmail(email != null ? email : existingPhotoGrapher.getEmail());
			existingPhotoGrapher
					.setMobileNumber(mobileNumber != null ? mobileNumber : existingPhotoGrapher.getMobileNumber());
			existingPhotoGrapher.setPassword(password != null ? password : existingPhotoGrapher.getPassword());

			return photoGrapherRepository.save(existingPhotoGrapher);
		});
	}

}
