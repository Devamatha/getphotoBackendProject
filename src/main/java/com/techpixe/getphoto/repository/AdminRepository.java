package com.techpixe.getphoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techpixe.getphoto.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>
{
	Admin findByEmail(String email);
	Admin findByMobileNumber(Long mobileNumber);

}
