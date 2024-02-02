package com.techpixe.getphoto.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techpixe.getphoto.dto.AdminDto;
import com.techpixe.getphoto.dto.ErrorResponseDto;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.repository.AdminRepository;
import com.techpixe.getphoto.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin registerAdmin(String fullName, String email, Long mobileNumber,String password) {
		Admin admin = new Admin();
		admin.setFullName(fullName);
		admin.setEmail(email);
		admin.setMobileNumber(mobileNumber);
		admin.setPassword(password);
//		this.generateRandomPassword();
		return adminRepository.save(admin);
	}

//	public void generateRandomPassword() {
//		// Generate a password with 4 alphabets and 4 numbers
//		Random random = new Random();
//		StringBuilder passwordBuilder = new StringBuilder();
//
//		for (int i = 0; i < 4; i++) {
//			// Generate a random alphabet (A-Z or a-z)
//			char randomAlphabet = (char) (random.nextInt(26) + 'A');
//			passwordBuilder.append(randomAlphabet);
//
//			// Generate a random number (0-9)
//			int randomNumber = random.nextInt(10);
//			passwordBuilder.append(randomNumber);
//		}
//		Admin admin = new Admin();
//		admin.setPassword(passwordBuilder.toString());
//
//	}
	
	
	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password)
	{
		Admin user = adminRepository.findByMobileNumber(mobileNumber);

		if (user != null && user.getPassword().equals(password))
		{
			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			return ResponseEntity.ok(applicationFormDTo);
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
		Admin user = adminRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password))
		{
			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			return ResponseEntity.ok(applicationFormDTo);

		} 
		else 
		{
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid email or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

}
