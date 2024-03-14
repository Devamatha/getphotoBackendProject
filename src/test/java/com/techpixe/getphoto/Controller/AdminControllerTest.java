//package com.techpixe.getphoto.Controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.Extension;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mysql.cj.util.TestUtils;
//import com.techpixe.getphoto.controller.AdminController;
//import com.techpixe.getphoto.entity.Admin;
//import com.techpixe.getphoto.service.AdminService;
//
//@ExtendWith(SpringExtension.class)
//public class AdminControllerTest {
//
//	    @Mock
//	    private AdminService adminService;
//
//	    @InjectMocks
//	    private AdminController adminController;
//
//	    @BeforeEach
//	    public void setUp() {
//	    	mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//	    }
//	    
//	    @Autowired
//	    MockMvc mockMvc;
//	    
//	    private ObjectMapper objectMapper;
//	    
//
//	    @Test
//	    public void testRegisterAdmin() throws JsonProcessingException, Exception {
//	    	 // Prepare mock data
////	        Admin mockAdmin = new Admin();
////	        mockAdmin.setFullName("Adilakshmi");
////	        mockAdmin.setEmail("adilakshmik2001@gmail.com");
////	        mockAdmin.setMobileNumber(7337081658L);
////	        mockAdmin.setPassword("paasowrd");
//	        
//	        // Convert mockAdmin to JSON
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        String adminJson = objectMapper.writeValueAsString(mockAdmin);
////	        
////	        // Set up the mock behavior with specific arguments
////	        when(adminService.registerAdmin("Adilakshmi", "adilakshmik2001@gmail.com", 7337081658L, "paasowrd"))
////	               .thenReturn(mockAdmin);
//	        
//	        // Perform the POST request
//	        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/Registratin")
//	        		.contentType(MediaType.APPLICATION_JSON)
//	        		.content(new ObjectMapper().writeValueAsString(new Admin("Adilakshmi", "adilakshmik2001@gmail.com", 7337081658L, "paasowrd"))).andReturn();
//	        
//            assertEquals(201, mvcResult.getResponse().getStatus());
//
//	    }
//}