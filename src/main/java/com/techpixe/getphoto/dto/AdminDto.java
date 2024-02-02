package com.techpixe.getphoto.dto;

public class AdminDto {
	private Long admin_Id;
	private String fullName;
	private String email;
	private Long mobileNumber;
	private String password;

	public Long getAdmin_Id() {
		return admin_Id;
	}

	public void setAdmin_Id(Long admin_Id) {
		this.admin_Id = admin_Id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
