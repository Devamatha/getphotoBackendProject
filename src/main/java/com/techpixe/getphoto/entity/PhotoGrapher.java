package com.techpixe.getphoto.entity;

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
	private String email;
	private Long mobileNumber;
	private String password;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="admin_Id")
	private Admin admin;
}
