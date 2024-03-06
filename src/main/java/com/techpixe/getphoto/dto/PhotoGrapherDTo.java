package com.techpixe.getphoto.dto;

public class PhotoGrapherDTo {
	private Long photographer_Id;
	private String email;

	private Long mobileNumber;
	private String password;
	private String fullName;
	private String role;
	private double subcriptionPlan;
	private long totalImages;

	public Long getPhotographer_Id() {
		return photographer_Id;
	}

	public void setPhotographer_Id(Long photographer_Id) {
		this.photographer_Id = photographer_Id;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public double getSubcriptionPlan() {
		return subcriptionPlan;
	}

	public void setSubcriptionPlan(double subcriptionPlan) {
		this.subcriptionPlan = subcriptionPlan;
	}

	public long getTotalImages() {
		return totalImages;
	}

	public void setTotalImages(long totalImages) {
		this.totalImages = totalImages;
	}

}
