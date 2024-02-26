package com.techpixe.getphoto.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.techpixe.getphoto.entity.ImageStoring;


public interface ImageStoringService {
	public String uploadImage(Long event, MultipartFile image) throws IOException;

	public void deleteimage(long id);
	ImageStoring fetchById(Long id);

}
