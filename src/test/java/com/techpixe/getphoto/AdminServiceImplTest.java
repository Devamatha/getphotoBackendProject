package com.techpixe.getphoto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.techpixe.getphoto.dto.AdminDto;
import com.techpixe.getphoto.dto.PhotoGrapherDTo;
import com.techpixe.getphoto.entity.Admin;
import com.techpixe.getphoto.entity.PhotoGrapher;
import com.techpixe.getphoto.repository.AdminRepository;
import com.techpixe.getphoto.repository.PhotoGrapherRepository;
import com.techpixe.getphoto.serviceImpl.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

	@Mock
	private AdminRepository adminRepository;

	@InjectMocks
	private AdminServiceImpl adminService;

	@Test
	public void testRegisterAdmin() {
		// Expected values
		String expectedFullName = "John Doe";
		String expectedEmail = "john@example.com";
		Long expectedMobileNumber = 1234567890L;
		String expectedPassword = "password";

		// Create a new Admin instance with expected values
		Admin expectedAdmin = new Admin();
		expectedAdmin.setFullName(expectedFullName);
		expectedAdmin.setEmail(expectedEmail);
		expectedAdmin.setMobileNumber(expectedMobileNumber);
		expectedAdmin.setPassword(expectedPassword);

		// Mock the repository to return the expected Admin instance
		when(adminRepository.save(any(Admin.class))).thenReturn(expectedAdmin);

		// Call the service method
		Admin registeredAdmin = adminService.registerAdmin(expectedFullName, expectedEmail, expectedMobileNumber,
				expectedPassword);
		System.out.println("expectedFullName" + expectedFullName);
		System.out.println("registeredAdmin.getFullName()" + registeredAdmin.getFullName());
		// Assert the values
		assertEquals(expectedFullName, registeredAdmin.getFullName());
		assertEquals(expectedEmail, registeredAdmin.getEmail());
		assertEquals(expectedMobileNumber, registeredAdmin.getMobileNumber());
		assertEquals(expectedPassword, registeredAdmin.getPassword());
	}

	
	
	
	@Test
	public void testRegisterFailure() {
		// Expected values
		String expectedFullName = "John Doe";
		String expectedEmail = "john@example.com";
		Long expectedMobileNumber = 1234567890L;
		String expectedPassword = "password";

		// Create a new Admin instance with Actual  values
		Admin expectedAdmin = new Admin();
		expectedAdmin.setFullName("jonFD");
		expectedAdmin.setEmail("duragaPradad@gmail.com");
		expectedAdmin.setMobileNumber(12345690L);
		expectedAdmin.setPassword("password");

		// Mock the repository to return the expected Admin instance
		when(adminRepository.save(any(Admin.class))).thenReturn(expectedAdmin);

		// Call the service method
		Admin registeredAdmin = adminService.registerAdmin(expectedFullName, expectedEmail, expectedMobileNumber,
				expectedPassword);
		System.out.println("expectedFullName" + expectedFullName);
		System.out.println("registeredAdmin.getFullName()" + registeredAdmin.getFullName());
		// Assert the values
		assertNotEquals(expectedFullName, registeredAdmin.getFullName());
		assertNotEquals(expectedEmail, registeredAdmin.getEmail());
		assertNotEquals(expectedMobileNumber, registeredAdmin.getMobileNumber());
		assertNotEquals(expectedPassword, registeredAdmin.getPassword());
	}

	@Test
	public void testLoginByEmail() {
		String email = "john@example.com";
		String password = "password";

		Admin admin = new Admin();
		admin.setEmail(email);
		admin.setPassword(password);

		when(adminRepository.findByEmail(email)).thenReturn(admin);

		ResponseEntity<?> responseEntity = adminService.loginByEmail(email, password);

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(email, ((AdminDto) responseEntity.getBody()).getEmail());
	}

	@Test
	public void testForgotPassword() {
		String email = "john@example.com";
		String newPassword = "newPassword123";

		Admin admin = new Admin();
		admin.setEmail(email);

		when(adminRepository.findByEmail(email)).thenReturn(admin);

		ResponseEntity<?> responseEntity = adminService.forgotPassword(email);

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(email, ((AdminDto) responseEntity.getBody()).getEmail());
		assertEquals(newPassword, ((AdminDto) responseEntity.getBody()).getPassword());
	}

	// Add more test methods for other scenarios as needed
}
