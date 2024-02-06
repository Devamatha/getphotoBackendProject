package com.techpixe.getphoto.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	@Override
	public Admin registerAdmin(String fullName, String email, Long mobileNumber,String password) {
		Admin admin = new Admin();
		admin.setFullName(fullName);
		admin.setEmail(email);
		admin.setMobileNumber(mobileNumber);
		admin.setPassword(password);
		admin.setRole("Admin");
		return adminRepository.save(admin);
	}


	
	
	@Override
	public ResponseEntity<?> loginByMobileNumber(Long mobileNumber, String password)
	{
		Admin user = adminRepository.findByMobileNumber(mobileNumber);
		PhotoGrapher user1 = PhotoGrapherRepository.findByMobileNumber(mobileNumber);

		if (user != null && user.getPassword().equals(password))
		{
			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			applicationFormDTo.setRole(user.getRole());

			return ResponseEntity.ok(applicationFormDTo);
		}
		else if(user1!=null && user1.getPassword().equals(password))
		{
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user1.getPhotographer_Id());
			photoGrapherDTo.setFullName(user1.getFullName());
			photoGrapherDTo.setEmail(user1.getEmail());
			photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
			photoGrapherDTo.setPassword(user1.getPassword());
			photoGrapherDTo.setRole(user1.getRole());
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
		Admin user = adminRepository.findByEmail(email);
		PhotoGrapher user1 = PhotoGrapherRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password))
		{
			AdminDto applicationFormDTo = new AdminDto();
			applicationFormDTo.setAdmin_Id(user.getAdmin_Id());
			applicationFormDTo.setFullName(user.getFullName());
			applicationFormDTo.setEmail(user.getEmail());
			applicationFormDTo.setMobileNumber(user.getMobileNumber());
			applicationFormDTo.setPassword(user.getPassword());
			applicationFormDTo.setRole(user.getRole());
			return ResponseEntity.ok(applicationFormDTo);

		} 
		else if(user1!=null && user1.getPassword().equals(password))
		{
			PhotoGrapherDTo photoGrapherDTo = new PhotoGrapherDTo();
			photoGrapherDTo.setPhotographer_Id(user1.getPhotographer_Id());
			photoGrapherDTo.setFullName(user1.getFullName());
			photoGrapherDTo.setEmail(user1.getEmail());
			photoGrapherDTo.setMobileNumber(user1.getMobileNumber());
			photoGrapherDTo.setPassword(user1.getPassword());
			photoGrapherDTo.setRole(user1.getRole());

			return ResponseEntity.ok(photoGrapherDTo);
		}
		else {
			ErrorResponseDto errorResponse = new ErrorResponseDto();
			errorResponse.setError("Invalid email or password");
			return ResponseEntity.internalServerError().body(errorResponse);
		}
	}

}
