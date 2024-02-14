package com.techpixe.getphoto.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoGrapher 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long photographer_Id;
	@Column(name="email", unique = true, nullable = false)
	private String email;
	
	@Column(name="mobileNumber", unique = true, nullable = false)
	private Long mobileNumber;
	
	private String password;
	
	private String fullName;
	
    private LocalDate registrationDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_Id")
	private Admin admin;
	private String role;



	
	
}
